package jpl.ch13.ex05;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberDividerTest {

    @Test
    public void testAddComma() {
        assertEquals("12,345,678", NumberDivider.addComma("12345678"));
        assertEquals("2,345,678", NumberDivider.addComma("2345678"));
        assertEquals("345,678", NumberDivider.addComma("345678"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddCommaWithAlphabetArgument() {
        NumberDivider.addComma("123a5678");
    }

}
