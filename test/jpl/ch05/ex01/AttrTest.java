package jpl.ch05.ex01;

import static org.junit.Assert.*;

import org.junit.Test;

public class AttrTest {

	@Test
	public void testConstructorAndGetters() {
		String name = "name";
		Object value = "value";
		
		Attr attr = new Attr(name, value);
		
		assertTrue(attr instanceof Attr);
		assertEquals(name, attr.getName());
		assertEquals((Object)value, attr.getValue());
	}
	
	@Test
	public void testSetValue() {
		String name = "name";
		Object oldValue = "oldvalue";
		Object newValue = "newvalue";
		
		Attr attr = new Attr(name, oldValue);
		
		Object result = attr.setValue(newValue);
		
		assertEquals((Object)newValue, attr.getValue());		
		assertEquals((Object)oldValue, result);		
	}
	
	@Test
	public void testToString() {
		String name = "name";
		Object value = "value";
		
		Attr attr = new Attr(name, value);
		
		String expectedString = "name='value'";
		
		assertEquals(expectedString, attr.toString());
		
	}

}
