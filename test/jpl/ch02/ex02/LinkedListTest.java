package jpl.ch02.ex02;

import static org.junit.Assert.*;

import org.junit.Test;

import jpl.ch02.ex06.LinkedList;

public class LinkedListTest {

	@Test
	public void testPublicFields() {
		Object object1 = "object1";
		
		LinkedList linkedList1 = new LinkedList();
		LinkedList linkedList2 = new LinkedList();
		
		linkedList1.object = object1;
		linkedList1.nextItem = linkedList2;
		
		assertEquals(object1, linkedList1.object);
		assertEquals(linkedList2, linkedList1.nextItem);
	}

}
