package jpl.ch21.ex05;

import java.util.AbstractList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayBunchList<E> extends AbstractList<E> {

    private final E[][] arrays;
    private final int size;
    
    public ArrayBunchList(E[][] arrays) {
        if (arrays == null) {
            throw new IllegalArgumentException("The argument should not be null.");
        } else {
            this.arrays = arrays.clone();
            int s = 0;
            for (E[] array : arrays) {
                if (array != null) {
                    for (E e : array) {
                        if (e != null) {
                            s++;
                        }
                    }
                }
            }
            size = s;
        }
    }

    @Override
    public E get(int index) {
        int off = 0;
        for (int i = 0; i < arrays[i].length; i++) {
            if (index < off + arrays[i].length) {
                return arrays[i][index - off];
            }
            off += arrays[i].length;
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }

    @Override
    public int size() {
        return size;
    }
    
    @Override
    public ListIterator<E> listIterator() {
        return new ABLListIterator();
    }
    
    @Override
    
    public E set(int index, E value) {
        int off = 0;
        for (int i = 0; i < arrays.length; i++) {
            if (index < off + arrays[i].length) {
                E ret = arrays[i][index = off];
                arrays[i][index - off] = value;
                return ret;
            }
            off += arrays[i].length;
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }
    
    private class ABLListIterator implements ListIterator<E> {
        int currentArrayInArrays = 0;
        int currentCursorInArray = 0;
        int cursor = -1;
        boolean cursorsAtInitialPositions = true;
        boolean lastCallWasNextFlag = false;
        boolean lastCallWasPreviousFlag = false;
        
        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("No implementation.");
        }

        @Override
        public boolean hasNext() {
            if (arrays == null || arrays.length == 0) {
                return false;
            }
            return cursor + 1 != size;
        }

        @Override
        public boolean hasPrevious() {
            if (arrays == null || arrays.length == 0) {
                return false;
            }
            return cursor > -1;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("End of the iterator.");            
            }
            if (cursorsAtInitialPositions) {
                cursorsAtInitialPositions = false;
                fastForwardIfNeeded();
            } else {
                currentCursorInArray++;
                fastForwardIfNeeded();
            }
            cursor++;
            setNextForLastCall();
            return arrays[currentArrayInArrays][currentCursorInArray];
        }
        
        private void fastForwardIfNeeded() {
            while (shouldFastForwardInArrays()) {
                currentCursorInArray = 0;
                currentArrayInArrays++;
            }
            while (shouldFastForwardInArray()) {
                currentCursorInArray++;
                fastForwardIfNeeded();
            }
        }

        private boolean shouldFastForwardInArrays() {
            return arrays[currentArrayInArrays] == null || arrays[currentArrayInArrays].length <= 0
                    || currentCursorInArray > arrays[currentArrayInArrays].length - 1;
        }
        
        private boolean shouldFastForwardInArray() {
            return arrays[currentArrayInArrays][currentCursorInArray] == null;
        }
        
        @Override
        public int nextIndex() {
            return cursor + 1;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("Beginning of the iterator.");            
            }
            E result = arrays[currentArrayInArrays][currentCursorInArray];
            cursor--;
            if (!hasPrevious()) {
                cursorsAtInitialPositions = true;
                return result;
            }
            currentCursorInArray--;
            rewindIfNeeded();
            setPreviousForLastCall();
            return result;
        }
        
        private void rewindIfNeeded() {
            while (shouldRewindInArrays()) {
                currentArrayInArrays--;
                if (arrays[currentArrayInArrays] != null) {
                    currentCursorInArray = arrays[currentArrayInArrays].length - 1;
                }
            }
            while (shouldRewindInArray()) {
                currentCursorInArray--;
                rewindIfNeeded();
            }
        }

        private boolean shouldRewindInArrays() {
            return arrays[currentArrayInArrays] == null || arrays[currentArrayInArrays].length <= 0
                    || currentCursorInArray < 0;
        }
        
        private boolean shouldRewindInArray() {
            return arrays[currentArrayInArrays][currentCursorInArray] == null;
        }
        
        @Override
        public int previousIndex() {
            return cursor;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("No implementation.");
        }

        @Override
        public void set(E e) {
            if (!lastCallWasNextOrPrevious()) {
                throw new IllegalStateException("Last call was neither next nor previous.");
            }
            if (e == null) {
                throw new IllegalArgumentException("The argument is null.");
            }
            int indexInArrays = currentArrayInArrays;
            int indexInArray = currentCursorInArray;
            if (lastCallWasPreviousFlag) {
                currentCursorInArray++;
                fastForwardIfNeeded();
                indexInArrays = currentArrayInArrays;
                indexInArray = currentCursorInArray;
                currentCursorInArray--;
                rewindIfNeeded();
            }
            arrays[indexInArrays][indexInArray] = e;
        }
        
        private boolean lastCallWasNextOrPrevious () {
            return lastCallWasNextFlag || lastCallWasPreviousFlag;
        }
        
        private void setNextForLastCall() {
            lastCallWasNextFlag = true;
            lastCallWasPreviousFlag = false;
        }
        private void setPreviousForLastCall() {
            lastCallWasNextFlag = false;
            lastCallWasPreviousFlag = true;
        }
    }

}
