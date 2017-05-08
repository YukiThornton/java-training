package jpl.ch22.ex01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class NumberTableTest {

    @Test
    public void testDoubleTable1() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        double[] data = {
                1.234567890123456789, 2345678.901234567891, 3.456789012345678912,
                4.567890123456789123, 5.678901234567891234, 6.789012345678912345,
                7.890123456789123456, 890123.4567891234567, 90.12345678912345678};
        NumberTable.showDoubleTable(data, 3);
        assertThat(out.toString(), is(
                "|    1.23|2.35e+06|    3.46|" + System.lineSeparator() +
                "|    4.57|    5.68|    6.79|" + System.lineSeparator() +
                "|    7.89|8.90e+05|    90.1|" + System.lineSeparator()
            ));
    }

    @Test
    public void testDoubleTable2() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        double[] data = {
                1.234567890123456789, 2345678.901234567891, 3.456789012345678912,
                4.567890123456789123, 5.678901234567891234, 6.789012345678912345,
                7.890123456789123456, 890123.4567891234567, 90.12345678912345678};
        NumberTable.showDoubleTable(data, 1);
        assertThat(out.toString(), is(
                "|    1.23|" + System.lineSeparator() +
                "|2.35e+06|" + System.lineSeparator() +
                "|    3.46|" + System.lineSeparator() +
                "|    4.57|" + System.lineSeparator() +
                "|    5.68|" + System.lineSeparator() +
                "|    6.79|" + System.lineSeparator() +
                "|    7.89|" + System.lineSeparator() + 
                "|8.90e+05|" + System.lineSeparator() + 
                "|    90.1|" + System.lineSeparator()
            ));
    }

    @Test
    public void testDoubleTable3() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        double[] data = {
                1.234567890123456789, 2345678.901234567891, 3.456789012345678912,
                4.567890123456789123, 5.678901234567891234, 6.789012345678912345,
                7.890123456789123456, 890123.4567891234567, 90.12345678912345678};
        NumberTable.showDoubleTable(data, 8);
        assertThat(out.toString(), is(
                "|    1.23|2.35e+06|    3.46|    4.57|    5.68|    6.79|    7.89|8.90e+05|" + System.lineSeparator() +
                "|    90.1|" + System.lineSeparator()
            ));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDoubleTableThrowsException1() {
        double[] data = {
                1.234567890123456789, 2345678.901234567891, 3.456789012345678912,
                4.567890123456789123, 5.678901234567891234, 6.789012345678912345,
                7.890123456789123456, 890123.4567891234567, 90.12345678912345678};
        NumberTable.showDoubleTable(data, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDoubleTableThrowsException2() {
        double[] data = {
                1.234567890123456789, 2345678.901234567891, 3.456789012345678912,
                4.567890123456789123, 5.678901234567891234, 6.789012345678912345,
                7.890123456789123456, 890123.4567891234567, 90.12345678912345678};
        NumberTable.showDoubleTable(data, 9);
    }

}
