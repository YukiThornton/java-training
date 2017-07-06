package java8.ch02.ex13;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class StreamPracticeTest {

    @Test
    public void testCountShortWords1() {
        testCountShortWords(200, 23);
    }

    @Test
    public void testCountShortWords2() {
        testCountShortWords(0, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCountShortWords3() {
        StreamPractice.countShortWords(null, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCountShortWords4() {
        StreamPractice.countShortWords(new ArrayList<>(), -1);
    }

    private void testCountShortWords(int wordCount, int maxLength) {
        String template = "***********************";
        List<String> list = new ArrayList<>(wordCount);
        int[] expected = new int[maxLength + 1];
        Random random = new Random();
        for (int i = 0; i < wordCount; i++) {
            int size = random.nextInt(maxLength + 1);
            list.add(template.substring(0, size));
            expected[size]++;
        }
        
        int[] result = StreamPractice.countShortWords(list, maxLength);
        assertTrue(result.length == expected.length);
        for (int i = 0; i < result.length; i++) {
            assertTrue(result[i] == expected[i]);
        }
    }

}
