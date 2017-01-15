package jpl.ch11.ex01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class LinkedListTest {

    @Test
    public void testSetAndGetElement() {
        String element1 = "element1";
        String element2 = "element2";

        LinkedList<String> linkedList1 = new LinkedList<String>(element1);

        assertEquals(element1, linkedList1.getElement());

        linkedList1.setElement(element2);

        assertEquals(element2, linkedList1.getElement());

    }

    @Test
    public void testSetAndGetNext() {
        String element1 = "element1";
        String element2 = "element2";
        
        LinkedList<String> linkedList1 = new LinkedList<String>(element1);
        LinkedList<String> linkedList2 = new LinkedList<String>(element2);

        linkedList1.setNext(linkedList2);
        
        assertEquals(linkedList2, linkedList1.getNext());
    
    }

    @Test
    public void testConstructorWithTwoParams() {
        String element1 = "element1";
        String element2 = "element2";
        
        LinkedList<String> linkedList2 = new LinkedList<String>(element2);
        LinkedList<String> linkedList1 = new LinkedList<String>(element1, linkedList2);
        
        assertEquals(element1, linkedList1.getElement());
        assertEquals(linkedList2, linkedList1.getNext());
    }

    @Test
    public void testLengthReturnsBiggerThanOne() {
        String element1 = "element1";
        String element2 = "element2";
        
        LinkedList<String> linkedList1 = new LinkedList<String>(element1);
        LinkedList<String> linkedList2 = new LinkedList<String>(element2);
        
        linkedList1.setNext(linkedList2);
        
        assertEquals((Integer)2, (Integer)linkedList1.length());
    }

    @Test
    public void testLengthReturnsOne() {
        String element1 = "element1";
        
        LinkedList<String> linkedList1 = new LinkedList<String>(element1);
        
        assertEquals((Integer)1, (Integer)linkedList1.length());
    }

    @Test
    public void testMain() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        LinkedList.main(new String[0]);
        
        assertThat(out.toString(), is("[element]Vehicle No.1: speed=0.0km/h, direction=0.0, owner=null " 
                + "[next]Vehicle No.2: speed=0.0km/h, direction=0.0, owner=null" + System.lineSeparator()));
    }
}
