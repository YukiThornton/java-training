package java8.ch02.ex05;

import java.util.stream.Stream;

public class StreamPractice {

    public static void main(String[] args) {
        StreamPractice practice = new StreamPractice();
        practice.generateRandomNumber(25214903917l, 11, (long)Math.pow(2, 48), 300000).limit(10).forEach(n -> System.out.println(n));

    }

    public Stream<Long> generateRandomNumber(long a, long c, long m, long seed) {
        return Stream.iterate(seed, n -> (a * n + c) % m);
    }
}
