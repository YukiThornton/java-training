package java8.ch02.ex03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class StreamPractice {

    public static void main(String[] args) throws IOException {
        StreamPractice practice = new StreamPractice();
        List<String> words = createWordList("src/java8/ch02/ex03/LoremIpsum.txt");
        int minLength = 4;

        long seqStart = System.nanoTime();
        practice.countLongWords(words, minLength);
        long seqEnd = System.nanoTime();

        long paraStart = System.nanoTime();
        practice.countLongWordsParallel(words, minLength);
        long paraEnd = System.nanoTime();

        System.out.println("Sequential: " + (seqEnd - seqStart));
        System.out.println("Parallel:   " + (paraEnd - paraStart));
    }

    private static List<String> createWordList(String pathname) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get(pathname)), StandardCharsets.UTF_8);
        return Arrays.asList(contents.split("[\\P{L}]+"));
    }

    public long countLongWords(List<String> words, int minLength) {
        if (words == null || minLength < 0) {
            throw new IllegalArgumentException("words != null && minLength >= 0");
        }
        return words.stream().filter(w -> w.length() >= minLength).count();
    }

    public long countLongWordsParallel(List<String> words, int minLength) {
        if (words == null || minLength < 0) {
            throw new IllegalArgumentException("words != null && minLength >= 0");
        }
        return words.parallelStream().filter(w -> w.length() >= minLength).count();
    }

}
