package jpl.ch13.ex06;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberDividerTest {

    @Test
    public void testAddComma() {
        assertEquals("12,34,56,78", NumberDivider.addComma("12345678", ',', 2));
        assertEquals("5*678", NumberDivider.addComma("5678", '*', 3));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddCommaWithAlphabetArgument() {
        NumberDivider.addComma("123a5678", ',', 2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddCommaWithMinusArgument() {
        NumberDivider.addComma("123a5678", ',', -2);
    }

}
