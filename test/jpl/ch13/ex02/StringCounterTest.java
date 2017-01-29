package jpl.ch13.ex02;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringCounterTest {

    @Test
    public void testCountChar() {
        assertEquals(1, StringCounter.countString("abcacbbca", "abc"));
        assertEquals(7, StringCounter.countString("aaabcaaaa", "a"));
    }
    @Test
    public void testCountCharReturnsMinus() {
        assertEquals(-1, StringCounter.countString("abcacbbca", "bac"));
    }
}
