package jpl.ch21.ex06;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

public class ConcatTest {

    @Test
    public void testMainOneFile() throws IOException {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        String[] fileNames = {"test/jpl/ch21/ex06/test1"};
        Concat.main(fileNames);
        assertThat(out.toString(), is(
                "abcdefg"
                ));
    }

    @Test
    public void testMainTwoFiles() throws IOException {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        String[] fileNames = {"test/jpl/ch21/ex06/test1", "test/jpl/ch21/ex06/test2"};
        Concat.main(fileNames);
        assertThat(out.toString(), is(
                "abcdefgh"
                ));
    }

}
