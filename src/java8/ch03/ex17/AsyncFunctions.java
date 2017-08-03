package java8.ch03.ex17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class AsyncFunctions {

    public static <T> void doInParallelAsync(Runnable first, Runnable second, Consumer<Throwable> handler) {
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(() -> {
            try {
                first.run();
            } catch(Throwable t) {
                handler.accept(t);
            }
        });
        pool.submit(() -> {
            try {
                second.run();
            } catch(Throwable t) {
                handler.accept(t);
            }
        });
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
