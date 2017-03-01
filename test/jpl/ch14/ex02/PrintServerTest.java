package jpl.ch14.ex02;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import jpl.ch14.ex02.PrintServer.PrintJob;

public class PrintServerTest {

    @Test
    public void testPrint() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        PrintJob job1 = new PrintJob("Job1");
        PrintJob job2 = new PrintJob("Job2");
        PrintServer server = new PrintServer();
        
        server.print(job1);
        server.print(job2);
        
        assertThat(out.toString(), is("Job1" + System.lineSeparator() + "Job2" + System.lineSeparator()));
    }

}
