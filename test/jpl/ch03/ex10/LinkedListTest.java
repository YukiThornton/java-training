package jpl.ch03.ex10;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class LinkedListTest {

	@Test
	public void testSetAndGetObject() {
		Object object1 = "object1";
		Object object2 = "object2";
		
		LinkedList linkedList1 = new LinkedList(object1);
		
		linkedList1.setObject(object2);
		
		assertEquals(object2, linkedList1.getObject());
	
	}

	@Test
	public void testSetAndGetNextItem() {
		Object object1 = "object1";
		Object object2 = "object2";
		
		LinkedList linkedList1 = new LinkedList(object1);
		LinkedList linkedList2 = new LinkedList(object2);
		
		linkedList1.setNextItem(linkedList2);
		
		assertEquals(linkedList2, linkedList1.getNextItem());
	
	}

	@Test
	public void testConstructorWithTwoParams() {
		Object object1 = "object1";
		Object object2 = "object2";
		
		LinkedList linkedList2 = new LinkedList(object2);
		LinkedList linkedList1 = new LinkedList(object1, linkedList2);
		
		assertEquals(object1, linkedList1.getObject());
		assertEquals(linkedList2, linkedList1.getNextItem());
	}
	
	@Test
	public void testLengthReturnsBiggerThanOne() {
		Object object1 = "object1";
		Object object2 = "object2";
		
		LinkedList linkedList1 = new LinkedList(object1);
		LinkedList linkedList2 = new LinkedList(object2);
		
		linkedList1.setNextItem(linkedList2);
		
		assertEquals((Integer)2, (Integer)linkedList1.length());
	}

	@Test
	public void testLengthReturnsOne() {
		Object object1 = "object1";
		
		LinkedList linkedList1 = new LinkedList(object1);
		
		assertEquals((Integer)1, (Integer)linkedList1.length());
	}
	
	@Test
	public void testClone() throws CloneNotSupportedException {
		
		Object object1 = "object1";
		Object object2 = "object2";
		
		LinkedList linkedList1 = new LinkedList(object1);
		LinkedList linkedList2 = new LinkedList(object2);
		
		linkedList1.setNextItem(linkedList2);
		
		LinkedList clonedList = linkedList1.clone();
		
		assertTrue(linkedList1.getObject() == clonedList.getObject());
		assertTrue(linkedList1.getNextItem().getObject() == clonedList.getNextItem().getObject());
		
	}

	@Test
	public void testMain() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		LinkedList.main(new String[0]);
		
		assertThat(out.toString(), is("Vehicle No.1: speed=0.0km/h, direction=0.0, owner=null; " 
				+ "Vehicle No.2: speed=0.0km/h, direction=0.0, owner=null; " 
				+ "Vehicle No.3: speed=0.0km/h, direction=0.0, owner=null" + System.lineSeparator()));
	}

}
