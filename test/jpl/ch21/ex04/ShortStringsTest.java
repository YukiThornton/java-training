package jpl.ch21.ex04;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.Test;

public class ShortStringsTest {

    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsException1() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        new ShortStrings(Arrays.asList(array).listIterator(), -1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsException2() {
        new ShortStrings(null, 2);
    }

    @Test
    public void testRoundTrip1() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        int[] filteredIndex = {0, 2, 4, 5};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        testHasNextAndNextAndNextIndex(shortStrings, array, filteredIndex);
        testHasPreviousAndPreviousAndPreviousIndex(shortStrings, array, filteredIndex);
    }

    @Test
    public void testRoundTrip2() {
        String[] array = {"aaa", "bb", "cc", "ddd", "ee", "fff"};
        int[] filteredIndex = {1, 2, 4};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        testHasNextAndNextAndNextIndex(shortStrings, array, filteredIndex);
        testHasPreviousAndPreviousAndPreviousIndex(shortStrings, array, filteredIndex);
    }

    @Test
    public void testRoundTrip3() {
        String[] array = {"aaa", "", "c", "ddd", "ee", "ff"};
        int[] filteredIndex = {1};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 0);
        
        testHasNextAndNextAndNextIndex(shortStrings, array, filteredIndex);
        testHasPreviousAndPreviousAndPreviousIndex(shortStrings, array, filteredIndex);
    }

    @Test
    public void testRoundTrip4() {
        String[] array = new String[0];
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        assertFalse(shortStrings.hasNext());
        assertFalse(shortStrings.hasPrevious());
    }

    @Test(expected=NoSuchElementException.class)
    public void testNextThrowsException() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        int[] filteredIndex = {0, 2, 4, 5};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        testHasNextAndNextAndNextIndex(shortStrings, array, filteredIndex);
        
        shortStrings.next();
    }

    @Test(expected=NoSuchElementException.class)
    public void testPreviousThrowsException() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.previous();
    }

    private void testHasNextAndNextAndNextIndex(ShortStrings shortStrings, String[] array, int[] filteredIndex) {
        
        for (int i = 0; i < filteredIndex.length; i++) {
            assertTrue(shortStrings.hasNext());
            assertTrue(shortStrings.nextIndex() == i);
            assertTrue(shortStrings.next().equals(array[filteredIndex[i]]));
        }
        
        assertFalse(shortStrings.hasNext());
        assertTrue(shortStrings.nextIndex() == filteredIndex.length);
    }

    private void testHasPreviousAndPreviousAndPreviousIndex(ShortStrings shortStrings, String[] array, int[] filteredIndex) {
        
        for (int i = filteredIndex.length - 1; i >= 0 ; i--) {
            assertTrue(shortStrings.hasPrevious());
            assertTrue(shortStrings.previousIndex() == i);
            assertTrue(shortStrings.previous().equals(array[filteredIndex[i]]));
        }
        
        assertFalse(shortStrings.hasPrevious());
        assertTrue(shortStrings.previousIndex() == -1);
    }

    @Test(expected=IllegalStateException.class)
    public void testSetThrowsException1() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.set("gg");
    }

    @Test(expected=IllegalStateException.class)
    public void testSetThrowsException2() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.add("gg");
        shortStrings.set("hh");
    }

    @Test(expected=IllegalStateException.class)
    public void testSetThrowsException3() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.remove();
        shortStrings.set("gg");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetThrowsException4() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.set("ggg");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetThrowsException5() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.set(null);
    }

    @Test
    public void testSetAfterNext() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.next();
        shortStrings.set("xx");
        
        assertTrue(shortStrings.hasNext());
        assertTrue(shortStrings.nextIndex() == 2);
        assertTrue(shortStrings.hasPrevious());
        assertTrue(shortStrings.previousIndex() == 1);
        assertTrue(shortStrings.previous().equals("xx"));
    }

    @Test
    public void testSetAfterPrevious() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.next();
        shortStrings.previous();
        shortStrings.set("xx");
        
        assertTrue(shortStrings.hasNext());
        assertTrue(shortStrings.nextIndex() == 1);
        assertTrue(shortStrings.hasPrevious());
        assertTrue(shortStrings.previousIndex() == 0);
        assertTrue(shortStrings.next().equals("xx"));
    }

    @Test
    public void testSetAfterSet() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.next();
        shortStrings.previous();
        shortStrings.set("xx");
        shortStrings.set("yy");
        
        assertTrue(shortStrings.hasNext());
        assertTrue(shortStrings.nextIndex() == 1);
        assertTrue(shortStrings.hasPrevious());
        assertTrue(shortStrings.previousIndex() == 0);
        assertTrue(shortStrings.next().equals("yy"));
    }

    @Test
    public void testAdd1() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.next();
        shortStrings.add("xx");
        
        assertTrue(shortStrings.hasNext());
        assertTrue(shortStrings.nextIndex() == 3);
        assertTrue(shortStrings.hasPrevious());
        assertTrue(shortStrings.previousIndex() == 2);
        assertTrue(shortStrings.previous().equals("xx"));
        assertTrue(shortStrings.next().equals("xx"));
        assertTrue(shortStrings.next().equals("ee"));
    }

    @Test
    public void testAdd2() {
        String[] array = new String[0];
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.add("xx");
        
        assertFalse(shortStrings.hasNext());
        assertTrue(shortStrings.hasPrevious());
        assertTrue(shortStrings.previousIndex() == 0);
        assertTrue(shortStrings.previous().equals("xx"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddThrowsException1() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.add("xxx");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddThrowsException2() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.add(null);
    }

    @Test(expected=IllegalStateException.class)
    public void testRemoveThrowsException1() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.remove();
    }

    @Test(expected=IllegalStateException.class)
    public void testRemoveThrowsException2() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.remove();
        shortStrings.remove();
    }

    @Test(expected=IllegalStateException.class)
    public void testRemoveThrowsException3() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.add("xx");
        shortStrings.remove();
    }

    @Test
    public void testRemoveAfterNext() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.next();
        shortStrings.remove();
        
        assertTrue(shortStrings.hasNext());
        assertTrue(shortStrings.nextIndex() == 1);
        assertTrue(shortStrings.hasPrevious());
        assertTrue(shortStrings.previousIndex() == 0);
        assertTrue(shortStrings.next().equals("ee"));
        assertTrue(shortStrings.previous().equals("ee"));
        assertTrue(shortStrings.previous().equals("aa"));
    }

    @Test
    public void testRemoveAfterPrevious() {
        String[] array = {"aa", "bbb", "cc", "ddd", "ee", "ff"};
        ShortStrings shortStrings = new ShortStrings(Arrays.asList(array).listIterator(), 2);
        
        shortStrings.next();
        shortStrings.next();
        shortStrings.previous();
        shortStrings.remove();
        
        assertTrue(shortStrings.hasNext());
        assertTrue(shortStrings.nextIndex() == 1);
        assertTrue(shortStrings.hasPrevious());
        assertTrue(shortStrings.previousIndex() == 0);
        assertTrue(shortStrings.next().equals("ee"));
        assertTrue(shortStrings.previous().equals("ee"));
        assertTrue(shortStrings.previous().equals("aa"));
    }

}
