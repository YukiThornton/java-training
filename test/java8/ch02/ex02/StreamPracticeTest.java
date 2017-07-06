package java8.ch02.ex02;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.junit.Test;

public class StreamPracticeTest {

    @Test
    public void testPrintLongWords1() {
        String[] arr = {"a", "abcd", "ab", "abcde", "abcdef", "ab", "abcdefg", "abcdefgh", "abcdefghi"};
        int[] indeces = {1, 3, 4, 6, 7};
        testPrintLongWords(arr, indeces);
    }

    @Test
    public void testPrintLongWords2() {
        String[] arr = {"a", "abcd", "ab", "abcde", "abcdef", "ab", "abcdefg"};
        int[] indeces = {1, 3, 4, 6};
        testPrintLongWords(arr, indeces);
    }

    @Test
    public void testPrintLongWords3() {
        String[] arr = {"a", "abc", "ab", "abc", "abc", "ab", "abc"};
        int[] indeces = {};
        testPrintLongWords(arr, indeces);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPrintLongWords4() {
        StreamPractice practice = new StreamPractice();
        practice.printLongWords(null, 4, 5);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPrintLongWords5() {
        String[] arr = {"a", "abcd", "ab", "abcde", "abcdef", "ab", "abcdefg", "abcdefgh", "abcdefghi"};
        StreamPractice practice = new StreamPractice();
        practice.printLongWords(Arrays.asList(arr), -1, 5);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPrintLongWords6() {
        String[] arr = {"a", "abcd", "ab", "abcde", "abcdef", "ab", "abcdefg", "abcdefgh", "abcdefghi"};
        StreamPractice practice = new StreamPractice();
        practice.printLongWords(Arrays.asList(arr), 4, -1);
    }

    private void testPrintLongWords(String[] words, int[] indeces) {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        StreamPractice practice = new StreamPractice();
        practice.printLongWords(Arrays.asList(words), 4, 5);
        
        String expected = "";
        for (int index: indeces) {
            expected += words[index]+ System.lineSeparator();
        }
        assertThat(out.toString(), is(expected));
    }

}
