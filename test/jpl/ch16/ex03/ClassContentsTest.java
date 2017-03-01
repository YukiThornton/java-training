package jpl.ch16.ex03;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class ClassContentsTest {

    @Test
    public void test() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        String[] args = {"java.util.LinkedList"};
        ClassContents.main(args);
        
        // should contain inherited member
        assertTrue(out.toString().contains("iterator"));
        
        // should contain non-inherited member only once
        assertEquals(2, out.toString().split("addFirst").length);
    }

}
