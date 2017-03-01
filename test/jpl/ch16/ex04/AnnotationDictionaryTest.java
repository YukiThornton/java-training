package jpl.ch16.ex04;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class AnnotationDictionaryTest {

    @Test
    public void testMain() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        String[] args1 = {"jpl.ch16.ex04.Foo"};
        AnnotationDictionary.main(args1);
        String[] args2 = {"jpl.ch16.ex04.Bar"};
        AnnotationDictionary.main(args2);
                
        // Annotation directly written on jpl.ch16.ex04.Foo should be printed.
        assertEquals(out.toString(), 
                "@jpl.ch16.ex04.ClassInfo(created=Today, createdBy=Me, lastModified=Today, lastModifiedBy=Me, revision=1)" + System.lineSeparator() + 
        
        // Annotation written on jpl.ch16.ex04.Bar's superclass should be printed.
                "@jpl.ch16.ex04.ClassInfo(created=Today, createdBy=Me, lastModified=Today, lastModifiedBy=Me, revision=1)" + System.lineSeparator());
    }

    @Test
    public void testMainClassWithNoAnnotation() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        String[] args1 = {"java.lang.String"};
        AnnotationDictionary.main(args1);
                
        assertEquals(out.toString(), 
                "No annotation was found." + System.lineSeparator());
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testMainWithEmptyArgument() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        String[] args1 = {};
        AnnotationDictionary.main(args1);
    }

}
