package java8.ch03.ex21;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class MyStreamTest {

    @Test
    public void test1() {
        ExecutorService pool = Executors.newCachedThreadPool();
        Future<List<String>> result = pool.submit(() -> {
            List<String> list = new ArrayList<>();
            try {
                for (int i = 0; i < 100; i++) {
                    list.add(Integer.toString(i));
                }
            } catch(Throwable t) {
            }
            return list;
        });
        Future<List<Integer>> result2 = MyStream.map(result, a -> {
            return java8.ch03.ex20.MyStream.map(a, s -> Integer.valueOf(s));
        });
        assertFalse(result2.isCancelled());
        assertFalse(result2.isDone());
        List<Integer> list = null;
        try {
            list = result2.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        assertFalse(result2.isCancelled());
        assertTrue(result2.isDone());
        assertTrue(list.size() == 100);
        for (int i = 0; i < 100; i++) {
            assertTrue(list.get(i).intValue() == i);
        }
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
