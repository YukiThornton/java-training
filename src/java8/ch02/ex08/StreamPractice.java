package java8.ch02.ex08;

import java.util.Iterator;
import java.util.stream.Stream;

public class StreamPractice {

    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Null!!");
        }
        Iterator<T> firstList = first.iterator();
        Iterator<T> secondList = second.iterator();
        Stream.Builder<T> builder = Stream.builder();
        while(true) {
            if (firstList.hasNext()) {
                builder.accept(firstList.next());
            } else {
                break;
            }
            if (secondList.hasNext()) {
                builder.accept(secondList.next());
            } else {
                break;
            }
        }
        return builder.build();
    }
}
