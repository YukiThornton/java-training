package jpl.ch22.ex05;

import static org.junit.Assert.*;

import org.junit.Test;

public class DiceTest {

    @Test
    public void testRoll1() {
        for (int i = 0; i < 100; i++) {
            int result = Dice.roll();
            assertTrue(result >= 1 && result <= 6);
        }
    }
    
    @Test
    public void testRoll2() {
        for (int i = 1; i < 51; i++) {
            int result = Dice.roll(i);
            assertTrue(result >= 1 && result <= 6 * i);
        }
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testRollThrowsException() {
        Dice.roll(0);
    }
    
    @Test
    public void testGetExpectedValues1() {
        int[] values = Dice.getExpectedDistribution(1);
        int[] expectedResult = {0, 1, 1, 1, 1, 1, 1};
        assertTrue(values.length == expectedResult.length);
        for (int i = 0; i < values.length; i++) {
            assertTrue(values[i] == expectedResult[i]);
        }
    }
    
    @Test
    public void testGetExpectedValues2() {
        int[] values = Dice.getExpectedDistribution(2);
        int[] expectedResult = {0, 0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1};
        assertTrue(values.length == expectedResult.length);
        for (int i = 0; i < values.length; i++) {
            assertTrue(values[i] == expectedResult[i]);
        }
    }
    
    @Test
    public void testGetExpectedValues3() {
        int[] values = Dice.getExpectedDistribution(3);
        int[] expectedResult = {0, 0, 0, 1, 3, 6, 10, 15, 21, 25, 27, 27, 25, 21, 15, 10, 6, 3, 1};
        assertTrue(values.length == expectedResult.length);
        for (int i = 0; i < values.length; i++) {
            assertTrue(values[i] == expectedResult[i]);
        }
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetExpectedValuesThrowsException() {
        Dice.getExpectedDistribution(0);
    }
    
    @Test
    public void testGetActualDistribution1() {
        int[] values = Dice.getActualDistribution(1, 1);
        assertTrue(values.length == 7);
    }
    
    @Test
    public void testGetActualDistribution2() {
        int[] values = Dice.getActualDistribution(3, 500);
        assertTrue(values.length == 19);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetActualDistributionThrowsException1() {
        Dice.getActualDistribution(0, 1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetActualDistributionThrowsException2() {
        Dice.getActualDistribution(1, 0);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testShowDistributionThrowsException1() {
        Dice.showDistribution(0, 1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testShowDistributionThrowsException2() {
        Dice.showDistribution(1, 0);
    }
}
