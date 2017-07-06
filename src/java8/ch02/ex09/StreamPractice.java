package java8.ch02.ex09;

import java.util.ArrayList;
import java.util.stream.Stream;

public class StreamPractice {

    public <T> ArrayList<T> merge1(Stream<ArrayList<T>> stream) {
        if (stream == null) {
           throw new IllegalArgumentException("Null!!!");
        }
        return stream.reduce((a, b) -> {
            a.addAll(b);
            return a;
        }).get();
    }

    public <T> ArrayList<T> merge2(Stream<ArrayList<T>> stream) {
        if (stream == null) {
            throw new IllegalArgumentException("Null!!!");
        }
        return stream.reduce(null, (a, b) -> {
            if (a == null) {
                return b;
            }
            a.addAll(b);
            return a;
        });
    }

    // テストに失敗する
    public <T> ArrayList<T> merge3(Stream<ArrayList<T>> stream) {
        if (stream == null) {
            throw new IllegalArgumentException("Null!!!");
         }
        return stream.reduce(null, (a, b) -> {
            if (a == null) {
                return b;
            }
            a.addAll(b);
            return a;
        }, (t1, t2) -> {
            ((ArrayList<T>)t1).addAll(t2);
            return t1;
        });
    }
}
