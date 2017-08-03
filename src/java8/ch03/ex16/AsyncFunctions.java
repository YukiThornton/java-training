package java8.ch03.ex16;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class AsyncFunctions {

    /*
     * ２つめの関数の実装の中で例外の有無による場合分けをしたい場合は、BiConsumerは有効
     * ３つめの引数の必要性は見いだせなかった
     */
    public static <T> void doInOrderAsync(Supplier<T> first, BiConsumer<T, Throwable> second) {
        new Thread(() -> {
            T result = null;
            Throwable throwable = null;
            try {
                result = first.get();
            } catch (Throwable e) {
                throwable = e;
            }
            second.accept(result, throwable);
        }).start();
    }

}
