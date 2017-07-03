package java8.ch02.ex04;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamPractice {

    public static void main(String[] args) {
        int[] values = {1, 4, 9, 16};
        // int配列のストリームが作成される
        Stream<int[]> arrayStream = Stream.of(values);
        // int用のStreamを使用すると、intのストリームが作成される
        IntStream intStream = IntStream.of(values);

        arrayStream.forEach(i -> System.out.println(i));
        intStream.forEach(i -> System.out.println(i));
    }

}
