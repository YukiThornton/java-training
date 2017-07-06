package java8.ch01.ex06;

import static org.junit.Assert.*;

import org.junit.Test;

public class RunnableExTest {

    @Test
    public void test() {
        new Thread(RunnableEx.uncheck(() -> {
            System.out.println("Zzzz");
            Thread.sleep(1000);
        }));
        assertTrue(true);
    }

}
