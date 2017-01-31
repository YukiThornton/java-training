package jpl.ch13.ex01;

import static org.junit.Assert.*;

import org.junit.Test;

public class CharCounterTest {

    @Test
    public void testCountChar() {
        assertEquals(3, CharCounter.countChar("abccbaabc", 'c'));
    }

    @Test
    public void testCountCharReturnsMinus() {
        assertEquals(-1, CharCounter.countChar("abcabcabc", 'd'));
    }

}
