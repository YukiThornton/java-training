package java8.ch03.ex02;

import static org.junit.Assert.*;

import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class LockToolsTest {

    private int inc1 = 0;

    @Test
    public void testWithLock1() throws InterruptedException {
        int threadCount = Thread.activeCount();
        ReentrantLock lock = new ReentrantLock();
        int amount = 100;
        for (int i = 0; i < amount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LockTools.withLock(lock, () -> {
                        increment1();
                    });
                }
            }).start();
        }
        while(Thread.activeCount() > threadCount) {
            Thread.sleep(100);
        }
        assertTrue(inc1 == amount);
    }

    private void increment1() {
        int i = inc1;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            fail();
        }
        inc1 = i + 1;
    }

    @Test(expected=NullPointerException.class)
    public void testWithLock2() {
        LockTools.withLock(null, () -> {
            ;
        });
    }

    @Test(expected=NullPointerException.class)
    public void testWithLock3() {
        ReentrantLock lock = new ReentrantLock();
        LockTools.withLock(lock, null);
    }

}
