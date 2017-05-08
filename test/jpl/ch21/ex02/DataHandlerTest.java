package jpl.ch21.ex02;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class DataHandlerTest {

    @Test
    public void testReadFile() throws IOException {
        File file = new File("test/jpl/ch21/ex02/test1.txt");
        DataHandler dataHandler = new DataHandler();
        dataHandler.readFile(file);
        
        Runtime runtime = Runtime.getRuntime();
        runtime.runFinalization();
        runtime.gc();
        fail("Not yet implemented");
    }

}
