package java8.ch02.ex13;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamPractice {

    public static int[] countShortWords(List<String> words, int maxLength) {
        if (words == null || maxLength < 0) {
            throw new IllegalArgumentException("words == null || maxLength < 0");
        }
        Map<Integer, Long> map = words.parallelStream().filter(w -> w.length() <= maxLength).collect(Collectors.groupingBy(String::length, Collectors.counting()));
        int[] result = new int[maxLength + 1];
        map.entrySet().stream().forEach(e -> result[e.getKey()] = Math.toIntExact(e.getValue()));
        return result;
    }

}
