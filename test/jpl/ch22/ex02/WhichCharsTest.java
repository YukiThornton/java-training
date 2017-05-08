package jpl.ch22.ex02;

import static org.junit.Assert.*;

import org.junit.Test;

public class WhichCharsTest {

    @Test
    public void testToString1() {
        WhichChars whichChars = new WhichChars("a2fgedg1cfb0g");
        assertTrue(whichChars.toString().equals("012abcdefg"));
    }

    @Test
    public void testToString2() {
        WhichChars whichChars = new WhichChars("");
        assertTrue(whichChars.toString().equals(""));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorThrowsException() {
        new WhichChars(null);
    }

}
