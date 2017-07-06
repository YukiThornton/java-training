package java8.ch02.ex08;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class StreamPracticeTest {

    @Test
    public void testZip1() {
        Integer[] first = {1, 3, 5, 7, 9};
        Integer[] second = {2, 4, 6, 8, 10};
        Integer[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        testZip(first, second, expected);
    }

    @Test
    public void testZip2() {
        Integer[] first = {1, 3, 5, 7};
        Integer[] second = {2, 4, 6, 8, 10};
        Integer[] expected = {1, 2, 3, 4, 5, 6, 7, 8};
        testZip(first, second, expected);
    }

    @Test
    public void testZip3() {
        Integer[] first = {1, 3, 5, 7, 9};
        Integer[] second = {2, 4, 6};
        Integer[] expected = {1, 2, 3, 4, 5, 6, 7};
        testZip(first, second, expected);
    }

    @Test
    public void testZip4() {
        Integer[] first = {};
        Integer[] second = {2, 4, 6};
        Integer[] expected = {};
        testZip(first, second, expected);
    }

    @Test
    public void testZip5() {
        Integer[] first = {1, 3, 5, 7, 9};
        Integer[] second = {};
        Integer[] expected = {1};
        testZip(first, second, expected);
    }

    @Test
    public void testZip6() {
        Integer[] first = {};
        Integer[] second = {};
        Integer[] expected = {};
        testZip(first, second, expected);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testZip7() {
        Integer[] second = {};
        StreamPractice.zip(null, Arrays.asList(second).stream());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testZip8() {
        Integer[] first = {};
        StreamPractice.zip(Arrays.asList(first).stream(), null);
    }

    private void testZip(Integer[] first, Integer[] second, Integer[] expected) {
        Integer[] result = StreamPractice.zip(Arrays.asList(first).stream(), Arrays.asList(second).stream()).toArray(Integer[]::new);
        assertTrue(expected.length == result.length);
        for (int i = 0; i < expected.length; i++) {
            assertTrue(expected[i].equals(result[i]));
        }
    }

}
