package java8.ch02.ex10;

import java.util.stream.Stream;

public class StreamPractice {

    private int count = 0;

    // １つのストリームに対して２つ以上の終端処理を行うことができないため、
    // 合計値を得て、かつ要素数も得るということはできない
    public double average(Stream<Double> stream) {
        if (stream == null) {
            throw new IllegalArgumentException("Null!!!");
        }
        Double total = stream.reduce(0d, (a, b) -> {
            count();
            return a + b;
        }, (t1, t2) -> {
            return t1 + t2;
        });
        return count == 0 ? 0 : total.doubleValue() / count;
    }

    private synchronized void count() {
        count++;
    }

}
