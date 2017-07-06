package java8.ch01.ex07;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;

public class RunnableToolsTest {

    @Test
    public void test() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        String[] words = {"Hello, ", "world."};
        new Thread(RunnableTools.andThen(
                () -> {
                    System.out.print(words[0]);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                () -> {
                    assertThat(out.toString(), is(words[0]));
                    System.out.print(words[1]);
                    assertThat(out.toString(), is(words[0] + words[1]));
                }
        )).start();
    }

}
