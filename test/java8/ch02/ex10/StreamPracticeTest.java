package java8.ch02.ex10;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamPracticeTest {

    @Test
    public void testAverage1() {
        Double[] input = createRandomDouble(100);
        double expected = getAverage(input);
        
        Stream<Double> stream = Arrays.asList(input).stream();
        
        StreamPractice practice = new StreamPractice();
        double result = practice.average(stream);
        assertTrue(result == expected);
    }

    @Test
    public void testAverage2() {
        Double[] input = createRandomDouble(100);
        double expected = getAverage(input);
        
        Stream<Double> stream = Arrays.asList(input).parallelStream();
        
        StreamPractice practice = new StreamPractice();
        double result = practice.average(stream);
        System.out.println("test2:expected  " + expected);
        System.out.println("test2:result    " + result);
        assertTrue(result == expected);
    }

    @Test
    public void testAverage3() {
        Double[] input = createRandomDouble(0);
        double expected = getAverage(input);
        
        Stream<Double> stream = Arrays.asList(input).stream();
        
        StreamPractice practice = new StreamPractice();
        double result = practice.average(stream);
        assertTrue(result == expected);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAverage4() {
        StreamPractice practice = new StreamPractice();
        practice.average(null);
    }


    private Double[] createRandomDouble(int count) {
        Double[] result = new Double[count];
        for (int i = 0; i < count; i++) {
            result[i] = Math.random();
        }
        return result;
    }

    private double getAverage(Double[] input) {
        if (input.length == 0) {
            return 0;
        }
        double total = 0;
        for (Double d: input) {
            total += d.doubleValue();
        }
        return total / input.length;
    }
}
