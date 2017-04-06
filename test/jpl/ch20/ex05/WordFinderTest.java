package jpl.ch20.ex05;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

public class WordFinderTest {

    @Test
    public void testFind1() throws IOException {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        WordFinder.find("test/jpl/ch20/ex05/test1.txt", "hello");

        assertThat(out.toString(), is("1: hello" + System.lineSeparator()
        + "5: oo hello" + System.lineSeparator() 
        + "6: hello" + System.lineSeparator()));
    }

    @Test
    public void testFind2() throws IOException {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        WordFinder.find("test/jpl/ch20/ex05/test1.txt", "world");

        assertThat(out.toString(), is(""));
    }

    @Test
    public void testFind3() throws IOException {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        WordFinder.find("test/jpl/ch20/ex05/test2.txt", "hello");

        assertThat(out.toString(), is(""));
    }

    @Test(expected=IOException.class)
    public void testFind4() throws IOException {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        WordFinder.find("test/jpl/ch20/ex05/testX.txt", "hello");
    }

}
