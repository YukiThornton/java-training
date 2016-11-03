package jpl.ch02.ex06;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

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

	@Test
	public void testMain() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		LinkedList.main(new String[0]);
		
		assertThat(out.toString(), is("The 1st item: Vehicle No.1, the next one is No.2" + System.lineSeparator()
			+ "The 2nd item: Vehicle No.2, the next one is No.3" + System.lineSeparator() 
			+ "The 3rd item: Vehicle No.3, the next one is null" + System.lineSeparator()
				));
	}

}
