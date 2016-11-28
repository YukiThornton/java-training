package jpl.ch03.ex07;

import static org.junit.Assert.*;

import org.junit.Test;

public class ColorAttrTest {

	@Test
	public void testNameAnsValueConstructorAndGetters() {
		String name = "name";
		Object value = "value";
		
		ColorAttr ca = new ColorAttr(name, value);
		
		assertTrue(ca instanceof Attr);
		assertTrue(ca instanceof ColorAttr);
		assertEquals(name, ca.getName());
		assertEquals((Object)value, ca.getValue());
		assertEquals((Object)value, ca.getColor().getValue());
	}
	
	@Test
	public void testNameConstructorAndGetters() {
		String name = "name";
		Object value = "transparent";
		
		ColorAttr ca = new ColorAttr(name);

		assertTrue(ca instanceof Attr);
		assertTrue(ca instanceof ColorAttr);
		assertEquals(name, ca.getName());
		assertEquals((Object)value, ca.getValue());
		assertEquals((Object)value, ca.getColor().getValue());
	}
	
	@Test
	public void testNameAndScreenColorConstructorAndGetters() {
		String name = "name";
		Object value = "value";
		ScreenColor screenColor = new ScreenColor(value);
		
		ColorAttr ca = new ColorAttr(name, screenColor);

		assertTrue(ca instanceof Attr);
		assertTrue(ca instanceof ColorAttr);
		assertEquals(name, ca.getName());
		assertEquals((Object)value, ca.getValue());
		assertEquals((Object)value, ca.getColor().getValue());
	}
	
	@Test
	public void testSetObjectValue() {
		String name = "name";
		Object oldValue = "oldvalue";
		Object newValue = "newvalue";
		
		ColorAttr ca = new ColorAttr(name, oldValue);
		
		Object returnValue = ca.setValue(newValue);

		assertEquals((Object)newValue, ca.getValue());
		assertEquals((Object)oldValue, returnValue);
		assertEquals((Object)newValue, ca.getColor().getValue());
	}
	
	@Test
	public void testSetScreenColorValue() {
		String name = "name";
		Object oldValue = "oldvalue";
		Object newValue = "newvalue";
		
		ColorAttr ca = new ColorAttr(name, oldValue);
		
		ScreenColor returnValue = ca.setValue(new ScreenColor(newValue));

		assertEquals((Object)newValue, ca.getValue());
		assertEquals((Object)oldValue, returnValue.getValue());
		assertEquals((Object)newValue, ca.getColor().getValue());
	}
	
	@Test
	public void testDecodeColorWhenValueIsNull() {
		String name = "name";
		Object oldValue = "oldvalue";
		Object newValue = null;
		ColorAttr ca = new ColorAttr(name, oldValue);
		ca.setValue(newValue);
		
		assertEquals(null, ca.getColor());
	}
	
	@Test
	public void testEqualsReturnsTrue() {
		String name1 = "name";
		Object value1 = "value";
		ColorAttr ca1 = new ColorAttr(name1, value1);
		String name2 = "name";
		Object value2 = "value";
		ColorAttr ca2 = new ColorAttr(name2, value2);
		
		assertTrue(ca1.equals(ca2));
		assertTrue(ca1.hashCode() == ca2.hashCode());
	}
	
	@Test
	public void testEqualsReturnsFalseForDifferentNames() {
		String name1 = "name";
		Object value1 = "value";
		ColorAttr ca1 = new ColorAttr(name1, value1);
		String name2 = "anothername";
		Object value2 = "value";
		ColorAttr ca2 = new ColorAttr(name2, value2);
		
		assertTrue(!ca1.equals(ca2));
		assertTrue(ca1.hashCode() != ca2.hashCode());
	}
	
	@Test
	public void testEqualsReturnsFalseForDifferentValues() {
		String name1 = "name";
		Object value1 = "value";
		ColorAttr ca1 = new ColorAttr(name1, value1);
		String name2 = "name";
		Object value2 = "anothervalue";
		ColorAttr ca2 = new ColorAttr(name2, value2);
		
		assertTrue(!ca1.equals(ca2));
		assertTrue(ca1.hashCode() != ca2.hashCode());
	}
	
	@Test
	public void testEqualsReturnsFalseForDifferentObjects() {
		String name1 = "name";
		Object value1 = "value";
		ColorAttr ca1 = new ColorAttr(name1, value1);
		String object2 = "name";
		
		assertTrue(!ca1.equals(object2));
		assertTrue(ca1.hashCode() != object2.hashCode());
	}
	
}
