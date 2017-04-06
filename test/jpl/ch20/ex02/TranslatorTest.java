package jpl.ch20.ex02;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

public class TranslatorTest {

    @Test
    public void testRead() throws IOException {
        InputStream in = new FileInputStream("test/jpl/ch20/ex02/test1.txt");
        OutputStream out = new ByteArrayOutputStream();
        
        FilterInputStream filterInput = new Translator(in, (byte)'l', (byte)'L');
        
        int b;
        while((b = filterInput.read()) != -1) {
            out.write(b);
        }
        assertThat(out.toString(), is("heLLo"));
        
        if (filterInput != null) {
            filterInput.close();
        }
        if (out != null) {
            out.close();
        }
    }

    @Test
    public void testReadNoMatching() throws IOException {
        InputStream in = new FileInputStream("test/jpl/ch20/ex02/test1.txt");
        OutputStream out = new ByteArrayOutputStream();
        
        FilterInputStream filterInput = new Translator(in, (byte)'p', (byte)'L');
        
        int b;
        while((b = filterInput.read()) != -1) {
            out.write(b);
        }
        assertThat(out.toString(), is("hello"));
        
        if (filterInput != null) {
            filterInput.close();
        }
        if (out != null) {
            out.close();
        }
    }

    @Test
    public void testReadZeroInput() throws IOException {
        InputStream in = new FileInputStream("test/jpl/ch20/ex02/test2.txt");
        OutputStream out = new ByteArrayOutputStream();
        
        FilterInputStream filterInput = new Translator(in, (byte)'l', (byte)'L');
        
        int b;
        while((b = filterInput.read()) != -1) {
            out.write(b);
        }
        assertThat(out.toString(), is(""));
        
        if (filterInput != null) {
            filterInput.close();
        }
        if (out != null) {
            out.close();
        }
    }

    @Test(expected=NullPointerException.class)
    public void testReadThrowsNullPointerException() throws IOException {
        FilterInputStream filterInput = null;
        OutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            
            filterInput = new Translator(null, (byte)'l', (byte)'L');
            
            filterInput.read();
            fail();
        } catch (NullPointerException e) {
            throw e;
        } finally {
            if (filterInput != null) {
                filterInput.close();
            }
            if (out != null) {
                out.close();
            }            
        }
    }


}
