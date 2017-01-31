package jpl.ch13.ex03;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringDividerTest {

    @Test
    public void testDelimitedString() {
        String from = "esestres";
        char start = 's';
        char end = 'e';
        String[] expected = new String[]{"se", "stre", "s"};
        String[] actual = StringDivider.delimitedString(from, start, end);
        
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testDelimitedStringWithSameCharArg() {
        String from = "esestres";
        char start = 's';
        char end = 's';
        String[] expected = new String[]{"ses", "stres", "s"};
        String[] actual = StringDivider.delimitedString(from, start, end);
        
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testDelimitedStringReturnsNull() {
        String from = "abcdefg";
        char start = 's';
        char end = 'e';
        String[] actual = StringDivider.delimitedString(from, start, end);
        
        assertTrue(actual == null);
    }

}
