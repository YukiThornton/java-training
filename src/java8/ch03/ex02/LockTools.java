package java8.ch03.ex02;

import java.util.concurrent.locks.ReentrantLock;

public class LockTools {

    public static void withLock(ReentrantLock lock, Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }
}
