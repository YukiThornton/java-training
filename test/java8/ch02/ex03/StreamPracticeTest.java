package java8.ch02.ex03;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class StreamPracticeTest {

    @Test
    public void testCountLongWords1() {
        String[] arr = {"a", "abcd", "ab", "abcde", "abcdef", "ab", "abcdefg", "abcdefgh", "abcdefghi"};
        testCountLongWords(arr, 4, 6);
    }

    @Test
    public void testCountLongWords2() {
        String[] arr = {"a", "abc", "ab", "abc", "abc", "ab", "abc"};
        testCountLongWords(arr, 4, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCountLongWords3() {
        StreamPractice practice = new StreamPractice();
        practice.countLongWords(null, 4);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCountLongWords4() {
        String[] arr = {"a", "abcd", "ab", "abcde", "abcdef", "ab", "abcdefg", "abcdefgh", "abcdefghi"};
        testCountLongWords(arr, -1, 6);
    }

    @Test
    public void testCountLongWordsParallel1() {
        String[] arr = {"a", "abcd", "ab", "abcde", "abcdef", "ab", "abcdefg", "abcdefgh", "abcdefghi"};
        testCountLongWordsParallel(arr, 4, 6);
    }

    @Test
    public void testCountLongWordsParallel2() {
        String[] arr = {"a", "abc", "ab", "abc", "abc", "ab", "abc"};
        testCountLongWordsParallel(arr, 4, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCountLongWordsParallel3() {
        StreamPractice practice = new StreamPractice();
        practice.countLongWordsParallel(null, 4);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCountLongWordsParallel4() {
        String[] arr = {"a", "abcd", "ab", "abcde", "abcdef", "ab", "abcdefg", "abcdefgh", "abcdefghi"};
        testCountLongWordsParallel(arr, -1, 6);
    }

    private void testCountLongWords(String[] words, int minLength, int count) {
        StreamPractice practice = new StreamPractice();
        assertTrue(count == practice.countLongWords(Arrays.asList(words), minLength));
    }

    private void testCountLongWordsParallel(String[] words, int minLength, int count) {
        StreamPractice practice = new StreamPractice();
        assertTrue(count == practice.countLongWordsParallel(Arrays.asList(words), minLength));
    }

}
