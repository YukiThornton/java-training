package jpl.ch21.ex05;

import static org.junit.Assert.*;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class ArrayBunchListTest {

    @Test(expected=IllegalArgumentException.class)
    public void testArrayBunchListConstructorThrowsException() {
        String[][] arrays = null;
        new ArrayBunchList<>(arrays);
    }

    @Test
    public void testRoundTrip1() {
        String[] array1 = {"aa", "bb"};
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = {"5", "66"};

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        String[] combinedArray = combineString(arrays);
        
        testHasNextAndNextAndNextIndex(iterator, combinedArray);
        testHasPreviousAndPreviousAndPreviousIndex(iterator, combinedArray);
        testBackAndForth(iterator, combinedArray);
    }
    @Test
    public void testRoundTrip2() {
        String[] array1 = {"aa", "bb"};

        String[][] arrays = {array1};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        String[] combinedArray = combineString(arrays);

        testHasNextAndNextAndNextIndex(iterator, combinedArray);
        testHasPreviousAndPreviousAndPreviousIndex(iterator, combinedArray);
    }

    @Test
    public void testRoundTrip3() {
        String[] array1 = null;
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = null;

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        String[] combinedArray = combineString(arrays);

        testHasNextAndNextAndNextIndex(iterator, combinedArray);
        testHasPreviousAndPreviousAndPreviousIndex(iterator, combinedArray);
        testBackAndForth(iterator, combinedArray);
    }

    @Test
    public void testRoundTrip3Twice() {
        String[] array1 = null;
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = null;

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        String[] combinedArray = combineString(arrays);

        testHasNextAndNextAndNextIndex(iterator, combinedArray);
        testHasPreviousAndPreviousAndPreviousIndex(iterator, combinedArray);
        testHasNextAndNextAndNextIndex(iterator, combinedArray);
        testHasPreviousAndPreviousAndPreviousIndex(iterator, combinedArray);
        testBackAndForth(iterator, combinedArray);
    }

    @Test
    public void testRoundTrip4() {
        String[][] arrays = {};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        String[] combinedArray = combineString(arrays);

        testHasNextAndNextAndNextIndex(iterator, combinedArray);
        testHasPreviousAndPreviousAndPreviousIndex(iterator, combinedArray);
    }

    @Test(expected=NoSuchElementException.class)
    public void testNextThrowsException() {
        String[] array1 = {"aa", "bb"};
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = {"5", "66"};

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        String[] combinedArray = combineString(arrays);

        testHasNextAndNextAndNextIndex(iterator, combinedArray);
        
        iterator.next();
    }

    @Test(expected=NoSuchElementException.class)
    public void testPreviousThrowsException() {
        String[] array1 = {"aa", "bb"};
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = {"5", "66"};

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        
        iterator.previous();
    }

    @Test
    public void testSetAfterNext() {
        String[] array1 = {"aa", "bb"};
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = {"5", "66"};

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        
        iterator.next();
        iterator.next();
        iterator.set("xx");
        
        assertTrue(iterator.hasNext());
        assertTrue(iterator.nextIndex() == 2);
        assertTrue(iterator.hasPrevious());
        assertTrue(iterator.previousIndex() == 1);
        assertTrue(iterator.previous().equals("xx"));
    }

    @Test
    public void testSetAfterPrevious() {
        String[] array1 = {"aa", "bb"};
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = {"5", "66"};

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        
        iterator.next();
        iterator.next();
        iterator.previous();
        iterator.set("xx");
        
        assertTrue(iterator.hasNext());
        assertTrue(iterator.nextIndex() == 1);
        assertTrue(iterator.hasPrevious());
        assertTrue(iterator.previousIndex() == 0);
        assertTrue(iterator.next().equals("xx"));
    }

    @Test
    public void testSetAfterSet() {
        String[] array1 = {"aa", "bb"};
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = {"5", "66"};

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        
        iterator.next();
        iterator.next();
        iterator.previous();
        iterator.set("xx");
        iterator.set("yy");
        
        assertTrue(iterator.hasNext());
        assertTrue(iterator.nextIndex() == 1);
        assertTrue(iterator.hasPrevious());
        assertTrue(iterator.previousIndex() == 0);
        assertTrue(iterator.next().equals("yy"));
    }

    @Test(expected=IllegalStateException.class)
    public void testSetThrowsException1() {
        String[] array1 = {"aa", "bb"};
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = {"5", "66"};

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        
        iterator.set("gg");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetThrowsException2() {
        String[] array1 = {"aa", "bb"};
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = {"5", "66"};

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        
        iterator.next();
        iterator.set(null);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testAdd() {
        String[] array1 = {"aa", "bb"};
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = {"5", "66"};

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        
        iterator.add("xx");
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testRemove() {
        String[] array1 = {"aa", "bb"};
        String[] array2 = {"cc", null, "ee"};
        String[] array3 = {};
        String[] array4 = {"111", "22", "3", ""};
        String[] array5 = {"5", "66"};

        String[][] arrays = {array1, array2, array3, array4, array5};
        ArrayBunchList<String> list = new ArrayBunchList<>(arrays);
        ListIterator<String> iterator = list.listIterator();
        
        iterator.remove();
    }

    private void testHasNextAndNextAndNextIndex(ListIterator<String> iterator, String[] array) {
        
        for (int i = 0; i < array.length; i++) {
            assertTrue(iterator.hasNext());
            assertTrue(iterator.nextIndex() == i);

            String nextStr = iterator.next();
            if (array[i] == null) {
                assertTrue(nextStr == null);
            } else {
                assertTrue(nextStr.equals(array[i]));
            }
        }
        
        assertFalse(iterator.hasNext());
        assertTrue(iterator.nextIndex() == array.length);
    }

    private void testBackAndForth(ListIterator<String> iterator, String[] array) {
        if (array.length < 4) {
            throw new IllegalArgumentException();
        } 
        String str0_0 = iterator.next();
        String str0_1 = iterator.previous();
        String str0_2 = iterator.next();
        String str1_0 = iterator.next();
        String str2_0 = iterator.next();
        String str2_1 = iterator.previous();
        String str1_1 = iterator.previous();
        String str0_3 = iterator.previous();
        
        assertTrue(str0_0.equals(array[0]));
        assertTrue(str0_1.equals(array[0]));
        assertTrue(str0_2.equals(array[0]));
        assertTrue(str0_3.equals(array[0]));
        assertTrue(str1_0.equals(array[1]));
        assertTrue(str1_1.equals(array[1]));
        assertTrue(str2_0.equals(array[2]));
        assertTrue(str2_1.equals(array[2]));

    }

    private void testHasPreviousAndPreviousAndPreviousIndex(ListIterator<String> iterator, String[] array) {
        
        for (int i = array.length - 1; i >= 0 ; i--) {
            assertTrue(iterator.hasPrevious());
            assertTrue(iterator.previousIndex() == i);
            String prevStr = iterator.previous();
            if (array[i] == null) {
                assertTrue(prevStr == null);
            } else {
                assertTrue(prevStr.equals(array[i]));
            }
        }
        
        assertFalse(iterator.hasPrevious());
        assertTrue(iterator.previousIndex() == -1);
    }
    
    private String[] combineString(String[][] arrays){
        int length = 0;
        for (String[] array : arrays) {
            if (array == null) {
                continue;
            }
            for (String str : array) {
                if (str == null) {
                    continue;
                }
                length++;
            }
        }
        String[] result = new String[length];
        int index = 0;
        for (String[] array : arrays) {
            if (array == null) {
                continue;
            }
            for (String str : array) {
                if (str == null) {
                    continue;
                }
                result[index] = str;
                index++;
            }
        }
        return result;
    }

}
