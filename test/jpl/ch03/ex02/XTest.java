package jpl.ch03.ex02;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class XTest {

	@Test
	public void testConstructor() {
		
		X x = new X();
		
		assertTrue(x instanceof X);
		assertEquals(0x00ff, x.xMask);
		assertEquals(0x00ff, x.fullMask);
	}

	@Test
	public void testMask() {
		int orig = 0x1010;
		
		X x = new X();
		int result = x.mask(orig);
		
		assertEquals(0x0010, result);
	}
	
	@Test
	public void testMain() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		X.main(new String[0]);
		
		assertThat(out.toString(), is("X initial block:" + System.lineSeparator()
		+ "xMask: 00ff" + System.lineSeparator()
		+ "fullMask: 0000" + System.lineSeparator() + System.lineSeparator()
		+ "At the last line of X constructor:" + System.lineSeparator()
		+ "xMask: 00ff" + System.lineSeparator()
		+ "fullMask: 00ff" + System.lineSeparator() + System.lineSeparator()
		+ "After mask with 1001" + System.lineSeparator()
		+ "xMask: 00ff" + System.lineSeparator()
		+ "fullMask: 00ff" + System.lineSeparator()
		+ "mask result: 0001" + System.lineSeparator()));
	}
}
