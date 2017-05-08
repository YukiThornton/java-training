package jpl.ch21.ex04;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ShortStrings implements ListIterator<String>{

    private List<String> list = new ArrayList<>();
    private int maxLen;
    private int cursor = -1;
    private int cursorOfLastReturn = -2;

    public ShortStrings(ListIterator<String> strings, int maxLen) {
        if (strings == null || maxLen < 0) {
            throw new IllegalArgumentException("strings is null or maxLen is smaller than 0.");
        }
        this.maxLen = maxLen;
        while(strings.hasNext()) {
            String nextStr = strings.next();
            if (nextStr.length() <= maxLen) {
                list.add(nextStr);
            }
        }
    }

    @Override
    public void add(String e) {
        if (e == null || e.length() > maxLen) {
            throw new IllegalArgumentException("The argument is null or the length is smaller than maxLen.");
        }
        list.add(++cursor, e);
        cursorOfLastReturn = -2;
    }

    @Override
    public boolean hasNext() {
        return nextIndex() < list.size();
    }

    @Override
    public boolean hasPrevious() {
        return previousIndex() >= 0;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("End of the iterator.");
        }
        cursorOfLastReturn = nextIndex();
        return list.get(++cursor);
    }

    @Override
    public int nextIndex() {
        return cursor + 1;
    }

    @Override
    public String previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException("Beginning of the iterator.");
        }
        cursorOfLastReturn = previousIndex();
        return list.get(cursor--);
    }

    @Override
    public int previousIndex() {
        return cursor;
    }

    @Override
    public void remove() {
        if (cursorOfLastReturn <= -2) {
            throw new IllegalStateException("Add or remove called after the last call of next or previous.");        
        }
        list.remove(cursorOfLastReturn);
        cursor = cursorOfLastReturn - 1;
        cursorOfLastReturn = -2;
    }

    @Override
    public void set(String e) {
        if (e == null || e.length() > maxLen) {
            throw new IllegalArgumentException("The argument is null or the length is smaller than maxLen.");
        }
        if (cursorOfLastReturn <= -2) {
            throw new IllegalStateException("Add or remove called after the last call of next or previous.");        
        }
        list.set(cursorOfLastReturn, e);
    }

}
