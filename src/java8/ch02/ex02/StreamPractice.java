package java8.ch02.ex02;

import java.util.Arrays;
import java.util.List;

public class StreamPractice {
    public static void main(String[] args) {
        String[] arr = {"a", "abcd", "ab", "abcde", "abcdef", "ab", "abcdefg", "abcdefgh", "abcdefghi"};
        StreamPractice practice = new StreamPractice();
        practice.printLongWords(Arrays.asList(arr), 4, 5);
    }

    public void printLongWords(List<String> words, int minLength, int limit) {
        if (words == null || minLength < 0 || limit < 0) {
            throw new IllegalArgumentException("words != null && minLength >= 0 && limit >= 0");
        }
        words.stream().filter(w -> w.length() >= minLength).limit(limit).forEach(w -> System.out.println(w));
    }
}
