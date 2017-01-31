package jpl.ch11.ex02;

import static org.junit.Assert.*;

import org.junit.Test;

import jpl.ch03.ex07.ScreenColor;

public class AttrTest {

    @Test
    public void testConstructorAndGetters() {
        String name = "name";
        String value = "value";
        
        Attr<String> attr = new Attr<String>(name, value);
        
        assertTrue(attr instanceof Attr);
        assertEquals(name, attr.getName());
        assertEquals(value, attr.getValue());
    }

    @Test
    public void testConstructorAndGettersWithScreenColor() {
        String name = "name";
        ScreenColor value = new ScreenColor("value");
        
        Attr<ScreenColor> attr = new Attr<ScreenColor>(name, value);
        
        assertTrue(attr instanceof Attr);
        assertEquals(name, attr.getName());
        assertEquals(value, attr.getValue());
    }

    @Test
    public void testSetValue() {
        String name = "name";
        String oldValue = "oldvalue";
        String newValue = "newvalue";
        
        Attr<String> attr = new Attr<String>(name, oldValue);
        
        String result = attr.setValue(newValue);
        
        assertEquals(newValue, attr.getValue());        
        assertEquals(oldValue, result);     
    }

    @Test
    public void testToString() {
        String name = "name";
        String value = "value";
        
        Attr<String> attr = new Attr<String>(name, value);
        
        String expectedString = "name='value'";
        
        assertEquals(expectedString, attr.toString());
        
    }
}
