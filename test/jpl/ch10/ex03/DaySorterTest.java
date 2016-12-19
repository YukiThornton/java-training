package jpl.ch10.ex03;

import static org.junit.Assert.*;

import org.junit.Test;

import jpl.ch06.ex01.Day;

public class DaySorterTest {

	// cannot test exception from isWorkday.
	@Test
	public void testIsWorkday() {
		assertTrue(DaySorter.isWorkday(Day.MONDAY));
		assertTrue(DaySorter.isWorkday(Day.TUESDAY));
		assertTrue(DaySorter.isWorkday(Day.WEDNESDAY));
		assertTrue(DaySorter.isWorkday(Day.THURSDAY));
		assertTrue(DaySorter.isWorkday(Day.FRIDAY));
		assertTrue(!DaySorter.isWorkday(Day.SATURDAY));
		assertTrue(!DaySorter.isWorkday(Day.SUNDAY));
	}

}
