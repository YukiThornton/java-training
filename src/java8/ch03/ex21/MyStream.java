package java8.ch03.ex21;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class MyStream {

    public static <T, U> Future<U> map(Future<T> src, Function<T, U> f) {
        return new Future<U>() {

            private boolean cancelled = false;
            private boolean taskCompleted = false;

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                if (isDone()) {
                    return false;
                } else {
                    cancelled = true;
                    return true;
                }
            }

            @Override
            public U get() throws InterruptedException, ExecutionException {
                if (!isDone()) {
                    U result = f.apply(src.get());
                    taskCompleted = true;
                    return result;
                } else {
                    throw new CancellationException();
                }
            }

            @Override
            public U get(long timeout, TimeUnit unit)
                    throws InterruptedException, ExecutionException, TimeoutException {
                if (!isDone()) {
                    U result = f.apply(src.get(timeout, unit));
                    taskCompleted = true;
                    return result;
                } else {
                    throw new CancellationException();
                }
            }

            @Override
            public boolean isCancelled() {
                return cancelled;
            }

            @Override
            public boolean isDone() {
                return taskCompleted || isCancelled();
            }
        
        };
    }

}
