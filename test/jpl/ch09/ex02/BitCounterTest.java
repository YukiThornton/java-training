package jpl.ch09.ex02;

import static org.junit.Assert.*;

import org.junit.Test;

public class BitCounterTest {

	@Test
	public void testAllFourDigitPatterns() {
		assertTrue(BitCounter.count(0x0) == 0);
		assertTrue(BitCounter.count(0x1) == 1);
		assertTrue(BitCounter.count(0x2) == 1);
		assertTrue(BitCounter.count(0x3) == 2);
		assertTrue(BitCounter.count(0x4) == 1);
		assertTrue(BitCounter.count(0x5) == 2);
		assertTrue(BitCounter.count(0x6) == 2);
		assertTrue(BitCounter.count(0x7) == 3);
		assertTrue(BitCounter.count(0x8) == 1);
		assertTrue(BitCounter.count(0x9) == 2);
		assertTrue(BitCounter.count(0xa) == 2);
		assertTrue(BitCounter.count(0xb) == 3);
		assertTrue(BitCounter.count(0xc) == 2);
		assertTrue(BitCounter.count(0xd) == 3);
		assertTrue(BitCounter.count(0xe) == 3);
		assertTrue(BitCounter.count(0xf) == 4);
	}

	@Test
	public void testShift() {
		assertTrue(BitCounter.count(0x1f) == 5);
		assertTrue(BitCounter.count(0x2f) == 5);
		assertTrue(BitCounter.count(0x4f) == 5);
		assertTrue(BitCounter.count(0x8f) == 5);
		assertTrue(BitCounter.count(0x10f) == 5);
	}

}
