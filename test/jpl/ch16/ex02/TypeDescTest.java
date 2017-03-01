package jpl.ch16.ex02;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class TypeDescTest {

    @Test
    public void test() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        String[] args = {"java.util.ResourceBundle$Control", "java.util.Map$Entry"};
        TypeDesc.main(args);
        
        assertThat(out.toString(), is(
                "class java.util.ResourceBundle.Control" + System.lineSeparator()
                + "  enclosed in java.util.ResourceBundle" + System.lineSeparator()
                + "interface java.util.Map.Entry<K, V, >" + System.lineSeparator()
                + "  enclosed in java.util.Map<K, V, >" + System.lineSeparator()
                ));
    }
    @Test
    public void testMainShowsNothing() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        String[] args = {};
        TypeDesc.main(args);
        
        assertThat(out.toString(), is(""));
    }
    

}
