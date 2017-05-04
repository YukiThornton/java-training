package jpl.ch22.ex14;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class DataReaderTest {

    @Test
    public void testSumDouble1() throws FileNotFoundException {
        double actual = DataReader.sumDouble("4.56 3.22 9 90.7 abc 0.4 7.45 oo 0 -9.3");
        assertTrue(actual == expected());
    }
    
    @Test
    public void testSumDouble2() throws FileNotFoundException {
        double actual = DataReader.sumDouble("");
        assertTrue(actual == 0);
    }
    
    @Test(expected=NullPointerException.class)
    public void testSumDoubleThrowsException() throws FileNotFoundException {
        DataReader.sumDouble(null);
    }
    
    private double expected() {
        double[] data = {4.56, 3.22, 9, 90.7, 0.4, 7.45, 0, -9.3};
        double sum = 0;
        for (double num : data) {
            sum += num;
        }
        return sum;
    }

}
