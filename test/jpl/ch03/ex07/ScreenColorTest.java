package jpl.ch03.ex07;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScreenColorTest {

	@Test
	public void testConstructorAndGetter() {
		Object value = "value";
		
		ScreenColor sc = new ScreenColor(value);
		
		assertTrue(sc instanceof ScreenColor);
		assertEquals(value, (Object)sc.getValue());
	}
	
	@Test
	public void testToString() {
		Object value = "value";
		
		ScreenColor sc = new ScreenColor(value);
		
		assertTrue(sc.toString().equals(value.toString()));		
	}

}
