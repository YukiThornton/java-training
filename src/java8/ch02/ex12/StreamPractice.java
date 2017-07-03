package java8.ch02.ex12;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class StreamPractice {

    public static int[] countShortWords(List<String> words, int maxLength) {
        if (words == null || maxLength < 0) {
            throw new IllegalArgumentException("words == null || maxLength < 0");
        }
        AtomicInteger[] integers = Stream.generate(AtomicInteger::new).limit(maxLength + 1).toArray(AtomicInteger[]::new);
        words.parallelStream().forEach(s -> {
            if (s.length() <= maxLength) {
                integers[s.length()].getAndIncrement();
            }
        });
        return Arrays.stream(integers).mapToInt(AtomicInteger::intValue).toArray();
    }

}
