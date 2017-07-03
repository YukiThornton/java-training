package java8.ch02.ex09;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamPracticeTest {

    @Test
    public void testMerge1_1() {
        String[][] values = createValues(5, 10);
        StreamPractice practice = new StreamPractice();

        Stream<ArrayList<String>> stream1 = createStream(values);
        ArrayList<String> result1 = practice.merge1(stream1);

        assertTrue(result1.size() == totalCount(values));
        for (String[] innerValues: values) {
            for (String value: innerValues) {
                assertTrue(result1.contains(value));
            }
        }
        
    }

    @Test
    public void testMerge1_2() {
        String[][] values = createValues(0, 10);
        StreamPractice practice = new StreamPractice();

        Stream<ArrayList<String>> stream1 = createStream(values);
        ArrayList<String> result1 = practice.merge1(stream1);

        assertTrue(result1.size() == totalCount(values));
        for (String[] innerValues: values) {
            for (String value: innerValues) {
                assertTrue(result1.contains(value));
            }
        }
        
    }

    @Test(expected=NoSuchElementException.class)
    public void testMerge1_3() {
        String[][] values = createValues(0, 0);
        StreamPractice practice = new StreamPractice();

        Stream<ArrayList<String>> stream1 = createStream(values);
        practice.merge1(stream1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMerge1_4() {
        StreamPractice practice = new StreamPractice();
        practice.merge1(null);
    }

    @Test
    public void testMerge2_1() {
        String[][] values = createValues(5, 10);
        StreamPractice practice = new StreamPractice();

        Stream<ArrayList<String>> stream2 = createStream(values);
        ArrayList<String> result2 = practice.merge2(stream2);

        assertTrue(result2.size() == totalCount(values));
        for (String[] innerValues: values) {
            for (String value: innerValues) {
                assertTrue(result2.contains(value));
            }
        }
        
    }

    @Test
    public void testMerge2_2() {
        String[][] values = createValues(0, 10);
        StreamPractice practice = new StreamPractice();

        Stream<ArrayList<String>> stream2 = createStream(values);
        ArrayList<String> result2 = practice.merge2(stream2);

        assertTrue(result2.size() == totalCount(values));
        for (String[] innerValues: values) {
            for (String value: innerValues) {
                assertTrue(result2.contains(value));
            }
        }
        
    }

    public void testMerge2_3() {
        String[][] values = createValues(0, 0);
        StreamPractice practice = new StreamPractice();

        Stream<ArrayList<String>> stream2 = createStream(values);
        ArrayList<String> result2 = practice.merge2(stream2);

        assertTrue(result2.size() == totalCount(values));
        for (String[] innerValues: values) {
            for (String value: innerValues) {
                assertTrue(result2.contains(value));
            }
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMerge2_4() {
        StreamPractice practice = new StreamPractice();
        practice.merge2(null);
    }

    @Test
    public void testMerge3_1() {
        String[][] values = createValues(5, 10);
        StreamPractice practice = new StreamPractice();

        Stream<ArrayList<String>> stream3 = createStream(values);
        ArrayList<String> result3 = practice.merge3(stream3);

        assertTrue(result3.size() == totalCount(values));
        for (String[] innerValues: values) {
            for (String value: innerValues) {
                assertTrue(result3.contains(value));
            }
        }
        
    }

    @Test
    public void testMerge3_2() {
        String[][] values = createValues(0, 10);
        StreamPractice practice = new StreamPractice();

        Stream<ArrayList<String>> stream3 = createStream(values);
        ArrayList<String> result3 = practice.merge3(stream3);

        assertTrue(result3.size() == totalCount(values));
        for (String[] innerValues: values) {
            for (String value: innerValues) {
                assertTrue(result3.contains(value));
            }
        }
        
    }

    @Test
    public void testMerge3_3() {
        String[][] values = createValues(0, 0);
        StreamPractice practice = new StreamPractice();

        Stream<ArrayList<String>> stream3 = createStream(values);
        ArrayList<String> result3 = practice.merge3(stream3);

        assertTrue(result3.size() == totalCount(values));
        for (String[] innerValues: values) {
            for (String value: innerValues) {
                assertTrue(result3.contains(value));
            }
        }
        
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMerge3_4() {
        StreamPractice practice = new StreamPractice();
        practice.merge3(null);
    }

    private String[][] createValues(int innerSize, int outerSize) {
        int count = 0;
        String[][] values = new String[outerSize][];
        for (int i = 0; i < outerSize; i++) {
            values[i] = new String[innerSize];
            for (int j = 0; j < innerSize; j++) {
                values[i][j] = "element" + (count++);
            }
        }
        return values;
    }
    private int totalCount(String[][] values) {
        int count = 0;
        for (String[] innerValues: values) {
            count += innerValues.length;
        }
        return count;
    }
    private Stream<ArrayList<String>> createStream(String[][] values) {
        ArrayList<ArrayList<String>> outerList = new ArrayList<>();
        if (values.length == 0) {
            return outerList.stream();
        }
        for (String[] innerValues: values) {
            ArrayList<String> innerList = new ArrayList<>();
            if (innerValues.length != 0) {
                innerList.addAll(Arrays.asList(innerValues));
            }
            outerList.add(innerList);
        }
        return outerList.stream();
    }
}
