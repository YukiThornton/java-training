package java8.ch03.ex23;

import java.util.function.Function;

public class Pair<T> {

    private T first;
    private T second;
    
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T second() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    public <R> Pair<R> map(Function<T, R> f) {
        return new Pair<R>(f.apply(first), f.apply(second));
    }
}
