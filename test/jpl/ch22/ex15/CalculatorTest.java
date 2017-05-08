package jpl.ch22.ex15;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculatorTest {

    @Test
    public void testCalculateLine1() {
        assertTrue(Calculator.calculateLine("1 2 +", " ") == 3);
        assertTrue(Calculator.calculateLine("1 2 -", " ") == -1);
        assertTrue(Calculator.calculateLine("2 3 *", " ") == 6);
        assertTrue(Calculator.calculateLine("1 2 /", " ") == 0.5);
        assertTrue(Calculator.calculateLine("15 7 %", " ") == 1);
        assertTrue(Calculator.calculateLine("15 cos", " ") == Math.cos(15));
        assertTrue(Calculator.calculateLine("3 5 copySign", " ") == Math.copySign(3, 5));
        assertTrue(Calculator.calculateLine("1 2 3 * +", " ") == 7);
        assertTrue(Calculator.calculateLine("1 60 3 4 * / +", " ") == 6);
    }

    @Test
    public void testCalculateLineThrowsException() {
        try {
            Calculator.calculateLine("", " ");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            Calculator.calculateLine("+", " ");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            Calculator.calculateLine("12 +", " ");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            Calculator.calculateLine("1 2 3 +", " ");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

}
