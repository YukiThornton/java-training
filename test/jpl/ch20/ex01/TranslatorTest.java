package jpl.ch20.ex01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

public class TranslatorTest {

    @Test
    public void testTranslateByte() throws IOException {
        InputStream in = new FileInputStream("test/jpl/ch20/ex01/test1.txt");
        OutputStream out = new ByteArrayOutputStream();
        
        Translator.translateByte(in, out, (byte)'l', (byte)'L');
        
        assertThat(out.toString(), is("heLLo"));
        
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }

    @Test
    public void testTranslateByteNoMatching() throws IOException {
        InputStream in = new FileInputStream("test/jpl/ch20/ex01/test1.txt");
        OutputStream out = new ByteArrayOutputStream();
        
        Translator.translateByte(in, out, (byte)'p', (byte)'L');
        
        assertThat(out.toString(), is("hello"));
        
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }

    @Test
    public void testTranslateByteZeroInput() throws IOException {
        InputStream in = new FileInputStream("test/jpl/ch20/ex01/test2.txt");
        OutputStream out = new ByteArrayOutputStream();
        
        Translator.translateByte(in, out, (byte)'l', (byte)'L');
        
        assertThat(out.toString(), is(""));
        
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testTranslateByteNullInputStream() throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        
        try {
            Translator.translateByte(null, out, (byte)'l', (byte)'L');
            fail();
        } catch(IllegalArgumentException e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }            
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testTranslateByteNullOutputStream() throws IOException {
        InputStream in = new FileInputStream("test/jpl/ch20/ex01/test1.txt");
        
        try {
            Translator.translateByte(null, null, (byte)'l', (byte)'L');
            fail();
        } catch(IllegalArgumentException e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }            
        }
    }

}
