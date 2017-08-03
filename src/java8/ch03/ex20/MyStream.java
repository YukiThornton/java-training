package java8.ch03.ex20;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyStream {

    public static <T, U> List<U> map(List<T> src, Function<T, U> f) {
        return src.stream().map(f).collect(Collectors.toList());
    }

}
