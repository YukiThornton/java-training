package jpl.ch13.ex04;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class WrapperConverterTest {

    @Test
    public void testConvertNormalCases() {
        String source = "Boolean true\nCharacter a\nByte 1\nShort 2\nInteger 3\nLong 4\nFloat 5.0\nDouble 6.0\n";
        ArrayList<Object> list = WrapperConverter.convert(source);
        assertEquals(8, list.size());
        assertEquals(true, ((Boolean)list.get(0)).booleanValue());
        assertEquals('a', ((Character)list.get(1)).charValue());
        assertEquals(1, ((Byte)list.get(2)).byteValue());
        assertEquals(2, ((Short)list.get(3)).shortValue());
        assertEquals(3, ((Integer)list.get(4)).intValue());
        assertEquals(4, ((Long)list.get(5)).longValue());
        assertTrue(5.0 == ((Float)list.get(6)).floatValue());
        assertTrue(6.0 == ((Double)list.get(7)).doubleValue());
    }

    @Test
    public void testConvertNullArgument() {
        String source = null;
        ArrayList<Object> list = WrapperConverter.convert(source);
        assertEquals(0, list.size());
    }

    @Test
    public void testConvertArgumentWithSentenceDivider() {
        String source = "Boolean trueCharacter aByte 1Short 2Integer 3Long 4Float 5.0Double 6.0";
        ArrayList<Object> list = WrapperConverter.convert(source);
        assertEquals(0, list.size());
    }

    @Test
    public void testConvertArgumentWithNoWordDivider() {
        String source = "Boolean true\nCharactera\nByte1\nShort2\nInteger3\nLong4\nFloat5.0\nDouble6.0\n";
        ArrayList<Object> list = WrapperConverter.convert(source);
        assertEquals(1, list.size());
        assertEquals(true, ((Boolean)list.get(0)).booleanValue());
    }

    @Test
    public void testConvertArgumentIllegalString() {
        String source = "Boolean tru\nCharacter aaaaaa\nByte aa\nShort b\nInteger c\nLong d\nFloat e\nDouble f\n";
        ArrayList<Object> list = WrapperConverter.convert(source);
        assertEquals(1, list.size());
        assertEquals(false, ((Boolean)list.get(0)).booleanValue());

        source = "Boolea true\nCharacer a\nyte 1\nShrt 2\nIntger 3\nLog 4\nFloa 5.0\nDoubl 6.0\n";
        list = WrapperConverter.convert(source);
        assertEquals(0, list.size());
    }

}
