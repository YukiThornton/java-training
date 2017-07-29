package java8.ch03.ex17;

import static org.junit.Assert.*;

import org.junit.Test;

public class AsyncFunctionsTest {

    @Test
    public void test1() {
        Counter firstCounter = new Counter();
        Counter secondCounter = new Counter();
        Counter handlerCounter = new Counter();
        AsyncFunctions.doInParallelAsync(() -> {
            count(firstCounter, 100, 101);
        }, () -> {
            count(secondCounter, 100, 101);
        }, t -> {
            handlerCounter.increment();
            handlerCounter.setThrowable(t);
        });
        assertTrue(firstCounter.val == 100);
        assertTrue(secondCounter.val == 100);
        assertTrue(handlerCounter.val == 0);
        assertTrue(handlerCounter.throwable == null);
    }

    @Test
    public void test2() {
        Counter firstCounter = new Counter();
        Counter secondCounter = new Counter();
        Counter handlerCounter = new Counter();
        AsyncFunctions.doInParallelAsync(() -> {
            count(firstCounter, 100, 50);
        }, () -> {
            count(secondCounter, 100, 101);
        }, t -> {
            handlerCounter.increment();
            handlerCounter.setThrowable(t);
        });
        assertTrue(firstCounter.val == 49);
        assertTrue(secondCounter.val == 100);
        assertTrue(handlerCounter.val == 1);
        assertTrue(handlerCounter.throwable != null);
    }

    @Test
    public void test3() {
        Counter firstCounter = new Counter();
        Counter secondCounter = new Counter();
        Counter handlerCounter = new Counter();
        AsyncFunctions.doInParallelAsync(() -> {
            count(firstCounter, 100, 101);
        }, () -> {
            count(secondCounter, 100, 50);
        }, t -> {
            handlerCounter.increment();
            handlerCounter.setThrowable(t);
        });
        assertTrue(firstCounter.val == 100);
        assertTrue(secondCounter.val == 49);
        assertTrue(handlerCounter.val == 1);
        assertTrue(handlerCounter.throwable != null);
    }

    @Test
    public void test4() {
        Counter firstCounter = new Counter();
        Counter secondCounter = new Counter();
        Counter handlerCounter = new Counter();
        AsyncFunctions.doInParallelAsync(() -> {
            count(firstCounter, 100, 30);
        }, () -> {
            count(secondCounter, 100, 50);
        }, t -> {
            handlerCounter.increment();
            handlerCounter.setThrowable(t);
        });
        assertTrue(firstCounter.val == 29);
        assertTrue(secondCounter.val == 49);
        assertTrue(handlerCounter.val == 2);
        assertTrue(handlerCounter.throwable != null);
    }

    private Counter count(Counter result, int max, int bombLocation) {
        for (int i = 0; i <= max; i++) {
            if (i == bombLocation) {
                throw new RuntimeException();
            }
            result.val = i;
        }
        return result;
    }
    class Counter {
        private int val = 0;
        private Throwable throwable = null;
        
        synchronized void increment() {
            this.val++;
        }

        
        synchronized void setThrowable(Throwable throwable) {
            this.throwable = throwable;
        }
    }
}
