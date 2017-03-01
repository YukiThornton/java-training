package jpl.ch16.ex01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class TypeDescTest {

    @Test
    public void testMain() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        String[] args = {"java.lang.String", "java.lang.Integer"};
        TypeDesc.main(args);
        
        assertThat(out.toString(), is(
                "class java.lang.String"  + System.lineSeparator()
                + "  implements java.io.Serializable"  + System.lineSeparator()
                + "  implements java.lang.Comparable<T, >" + System.lineSeparator()
                + "  implements java.lang.CharSequence" + System.lineSeparator()
                + "class java.lang.Integer"  + System.lineSeparator()
                + "  implements java.lang.Comparable<T, >" + System.lineSeparator()
                + "  extends java.lang.Number" + System.lineSeparator()
                + "    implements java.io.Serializable" + System.lineSeparator()
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
