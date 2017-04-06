package jpl.ch17.ex02;

import static org.junit.Assert.*;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class DataHandlerTest {

    @Test
    public void testReadFileOnce() throws UnsupportedEncodingException {
        DataHandler dataHandler = new DataHandler();
        File file = new File("test/jpl/ch17/ex02/sample1.txt");
        
        byte[] bytes = dataHandler.readFile(file);
        
        assertTrue(bytes.length == 3);
        assertTrue(new String(bytes, "UTF-8").equals("abc"));
    }

    @Test
    public void testReadSameFileTwice() throws UnsupportedEncodingException {
        DataHandler dataHandler = new DataHandler();
        String path = "test/jpl/ch17/ex02/sample1.txt";
        
        File file = new File(path);
        byte[] bytes = dataHandler.readFile(file);
        file = null;
        
        file = new File(path);
        bytes = dataHandler.readFile(file);

        assertTrue(bytes.length == 3);
        assertTrue(new String(bytes, "UTF-8").equals("abc"));
    }

    @Test
    public void testReadTwoDifferentFiles() throws UnsupportedEncodingException {
        DataHandler dataHandler = new DataHandler();
        
        File file = new File("test/jpl/ch17/ex02/sample1.txt");
        byte[] bytes = dataHandler.readFile(file);
        file = null;
        
        file = new File("test/jpl/ch17/ex02/sample2.txt");
        bytes = dataHandler.readFile(file);

        assertTrue(bytes.length == 3);
        assertTrue(new String(bytes, "UTF-8").equals("def"));
    }

    @Test
    public void testReadFileOnceNoFileFound() throws UnsupportedEncodingException {
        DataHandler dataHandler = new DataHandler();
        File file = new File("test/jpl/ch17/ex02/sampleX.txt");
        
        byte[] bytes = dataHandler.readFile(file);
        
        assertTrue(bytes.length == 0);
    }

}
