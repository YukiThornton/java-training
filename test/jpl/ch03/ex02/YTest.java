package jpl.ch03.ex02;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class YTest {

	@Test
	public void testConstructor() {
		
		Y y = new Y();
		
		assertTrue(y instanceof X);
		assertTrue(y instanceof Y);
		assertEquals(0x00ff, y.xMask);
		assertEquals(0xff00, y.yMask);
		assertEquals(0xffff, y.fullMask);
	}
	@Test
	public void testMain() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		Y.main(new String[0]);
		
		assertThat(out.toString(), is("X initial block:" + System.lineSeparator()
		+ "xMask: 00ff" + System.lineSeparator()
		+ "fullMask: 0000" + System.lineSeparator() + System.lineSeparator()
		+ "At the last line of X constructor:" + System.lineSeparator()
		+ "xMask: 00ff" + System.lineSeparator()
		+ "fullMask: 00ff" + System.lineSeparator() + System.lineSeparator()
		+ "Y initial block:" + System.lineSeparator()
		+ "xMask: 00ff" + System.lineSeparator()
		+ "yMask: ff00" + System.lineSeparator()
		+ "fullMask: 00ff" + System.lineSeparator() + System.lineSeparator()
		+ "At the first line of Y constructor:" + System.lineSeparator()
		+ "xMask: 00ff" + System.lineSeparator()
		+ "yMask: ff00" + System.lineSeparator()
		+ "fullMask: 00ff" + System.lineSeparator() + System.lineSeparator()
		+ "At the last line of Y constructor:" + System.lineSeparator()
		+ "xMask: 00ff" + System.lineSeparator()
		+ "yMask: ff00" + System.lineSeparator()
		+ "fullMask: ffff" + System.lineSeparator() + System.lineSeparator()
		+ "After mask with 1001" + System.lineSeparator()
		+ "xMask: 00ff" + System.lineSeparator()
		+ "yMask: ff00" + System.lineSeparator()
		+ "fullMask: ffff" + System.lineSeparator()
		+ "mask result: 1001" + System.lineSeparator()));
	}

}
