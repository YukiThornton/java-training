package jpl.ch04.ex03;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class LinkedStringTest {

	@Test
	public void testSetAndGetObject() {
		String string1 = "string1";
		String string2 = "string2";
		
		LinkedList linkedList1 = new LinkedString(string1);
		
		linkedList1.setObject(string2);
		
		assertEquals(string2, (String)linkedList1.getObject());
	
	}

	@Test
	public void testSetAndGetNextItem() {
		String string1 = "string1";
		String string2 = "string2";
		
		LinkedList linkedList1 = new LinkedString(string1);
		LinkedList linkedList2 = new LinkedString(string2);
		
		linkedList1.setNextItem(linkedList2);
		
		assertEquals(linkedList2, linkedList1.getNextItem());
	
	}

	@Test
	public void testConstructorWithTwoParams() {
		String string1 = "string1";
		String string2 = "string2";
		
		LinkedList linkedList2 = new LinkedString(string2);
		LinkedList linkedList1 = new LinkedString(string1, linkedList2);
		
		assertEquals(string1, (String)linkedList1.getObject());
		assertEquals(linkedList2, linkedList1.getNextItem());
	}
	
	@Test
	public void testLengthReturnsBiggerThanOne() {
		String string1 = "string1";
		String string2 = "string2";
		
		LinkedList linkedList1 = new LinkedString(string1);
		LinkedList linkedList2 = new LinkedString(string2);
		
		linkedList1.setNextItem(linkedList2);
		
		assertEquals((Integer)2, (Integer)linkedList1.length());
	}

	@Test
	public void testLengthReturnsOne() {
		String string1 = "string1";
		
		LinkedList linkedList1 = new LinkedString(string1);
		
		assertEquals((Integer)1, (Integer)linkedList1.length());
	}
	
	@Test
	public void testClone() {
		String string1 = "string1";
		String string2 = "string2";
		
		LinkedList linkedList1 = new LinkedString(string1);
		LinkedList linkedList2 = new LinkedString(string2);
		
		linkedList1.setNextItem(linkedList2);
		
		LinkedList clonedList = linkedList1.clone();
		
		assertTrue(linkedList1.getObject() == clonedList.getObject());
		assertTrue(linkedList1.getNextItem() == clonedList.getNextItem());
		
	}

	@Test
	public void testMain() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		LinkedString.main(new String[0]);
		
		assertThat(out.toString(), is("string1; string2; string3" + System.lineSeparator()));
	}

}
