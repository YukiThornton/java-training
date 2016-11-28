package jpl.ch03.ex06;

import static org.junit.Assert.*;

import org.junit.Test;

import jpl.ch03.ex06.EnergySource.WrongEnegryUsageException;

public class BatteryTest {

	@Test
	public void testConstructorWithTwoParams() {
		double max = 12;
		double current = 10;
		Battery battery = null;
		
		try {
			battery = new Battery(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertTrue(battery instanceof Battery);
		assertTrue(battery instanceof EnergySource);
		assertEquals((Double)max, (Double)battery.getMaxAmountl());
		assertEquals((Double)current, (Double)battery.getCurrentAmount());
	}

	@Test(expected = WrongEnegryUsageException.class)
	public void testConstructorWithTwoParamsInvalidMax() throws WrongEnegryUsageException {
		double max = 0;
		double current = 0;
		
		new Battery(max, current);
	}

	@Test(expected = WrongEnegryUsageException.class)
	public void testConstructorWithTwoParamsInvalidCurrentAmount() throws WrongEnegryUsageException {
		double max = 0.1;
		double current = -0.1;
		
		new Battery(max, current);
	}

	@Test(expected = WrongEnegryUsageException.class)
	public void testConstructorWithTwoParamsGreaterCurrentAmount() throws WrongEnegryUsageException {
		double max = 0.1;
		double current = 0.2;
		
		new Battery(max, current);
	}

	@Test
	public void testConstructorWithOneParam() {
		double max = 12;
		Battery battery = null;
		
		try {
			battery = new Battery(max);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertTrue(battery instanceof Battery);
		assertTrue(battery instanceof EnergySource);
		assertEquals((Double)max, (Double)battery.getMaxAmountl());
		assertEquals((Double)max, (Double)battery.getCurrentAmount());
	}

	@Test
	public void testEmptyReturnsFalse() {
		double max = 12;
		Battery battery = null;
		
		try {
			battery = new Battery(max);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertTrue(!battery.empty());
	}

	@Test
	public void testEmptyReturnsTrue() {
		double max = 12;
		double current = 0;
		Battery battery = null;
		
		try {
			battery = new Battery(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertTrue(battery.empty());
	}

	@Test
	public void testFillUpWhenFilledUp() {
		double max = 12;
		Battery battery = null;
		
		try {
			battery = new Battery(max);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		battery.fillUp();
		
		assertEquals((Double)max, (Double)battery.getCurrentAmount());
	}

	@Test
	public void testFillUpWhenEmpty() {
		double max = 12;
		double current = 0;
		Battery battery = null;
		
		try {
			battery = new Battery(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		battery.fillUp();
		
		assertEquals((Double)max, (Double)battery.getCurrentAmount());
	}

	@Test
	public void testUse() {
		double max = 12;
		double current = 10;
		double cost = 2;
		Battery battery = null;
		
		try {
			battery = new Battery(max, current);
			
			battery.use(cost);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertEquals((Double)(current - cost), (Double)battery.getCurrentAmount());
	}

	@Test(expected = WrongEnegryUsageException.class)
	public void testUseWithNegativeParam() throws WrongEnegryUsageException {
		double max = 12;
		double current = 10;
		double cost = -2;
		Battery battery = null;
		
		try {
			battery = new Battery(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		try {
			battery.use(cost);
		} catch (OutOfEnergyException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
	}

	@Test(expected = OutOfEnergyException.class)
	public void testUseWithTooMuchCost() throws OutOfEnergyException {
		double max = 12;
		double current = 10;
		double cost = 11;
		Battery battery = null;
		
		try {
			battery = new Battery(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		try {
			battery.use(cost);
		} catch (WrongEnegryUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
	}

}
