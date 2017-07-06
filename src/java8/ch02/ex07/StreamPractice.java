package java8.ch02.ex07;

import java.util.stream.Stream;

public class StreamPractice {

    public static void main(String[] args) {
        Stream<Double> stream = Stream.generate(Math::random);
        isFinite(stream);

    }

    public static <T> boolean isFinite(Stream<T> stream) {
        // 以下のような終端処理を行ったり、peekメソッドで何かしらの処理をおこなって永遠にすべての処理が終わらないことを監視することはできるが
        // どのタイミングで終わらないことを判断するか決めることができない。よってこのメソッドは作るべきではない。
        //long count = stream.count();
        return false;
    }
}
