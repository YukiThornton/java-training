package java8.ch03.ex01;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class MyLoggerTest {

    @Test
    public void testLogif1() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setErr(new PrintStream(out));
        Logger logger = Logger.getLogger(MyLoggerTest.class.getName());
        MyLogger myLogger = new MyLogger(logger);

        String message1 = "message1";
        String message2 = "message2";
        myLogger.logif(Level.INFO, () -> true, () -> message1 + message2);

        assertTrue(out.toString().contains(message1 + message2));
    }

    @Test
    public void testLogif2() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setErr(new PrintStream(out));
        Logger logger = Logger.getLogger(MyLoggerTest.class.getName());
        MyLogger myLogger = new MyLogger(logger);

        String message1 = "message1";
        String message2 = "message2";
        myLogger.logif(Level.INFO, () -> false, () -> {
            fail();
            return message1 + message2;
        });

        assertFalse(out.toString().contains(message1 + message2));
    }

    @Test
    public void testLogif3() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setErr(new PrintStream(out));
        Logger logger = Logger.getLogger(MyLoggerTest.class.getName());
        MyLogger myLogger = new MyLogger(logger);

        String message1 = "message1";
        String message2 = "message2";
        myLogger.logif(Level.FINEST, () -> {
            fail();
            return false;
        }, () -> {
            fail();
            return message1 + message2;
        });

        assertFalse(out.toString().contains(message1 + message2));
    }

}
