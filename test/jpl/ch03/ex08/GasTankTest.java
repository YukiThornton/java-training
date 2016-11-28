package jpl.ch03.ex08;

import static org.junit.Assert.*;

import org.junit.Test;

import jpl.ch03.ex08.EnergySource.WrongEnegryUsageException;

public class GasTankTest {

	@Test
	public void testConstructorWithTwoParams() {
		double max = 12;
		double current = 12;
		GasTank gt = null;
		
		try {
			gt = new GasTank(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertTrue(gt instanceof GasTank);
		assertTrue(gt instanceof EnergySource);
		assertEquals((Double)max, (Double)gt.getMaxGasLevel());
		assertEquals((Double)current, (Double)gt.getGasLevel());
	}

	@Test(expected = WrongEnegryUsageException.class)
	public void testConstructorWithTwoParamsInvalidMax() throws WrongEnegryUsageException {
		double max = 0;
		double current = 0;
		new GasTank(max, current);
	}
	
	@Test(expected = WrongEnegryUsageException.class)
	public void testConstructorWithTwoParamsInvalidGasLevel() throws WrongEnegryUsageException {
		double max = 0.1;
		double current = -0.1;
		new GasTank(max, current);
	}
	
	@Test(expected = WrongEnegryUsageException.class)
	public void testConstructorWithTwoParamsGreaterGasLevel() throws WrongEnegryUsageException {
		double max = 0.1;
		double current = 0.2;
		new GasTank(max, current);
	}
	
	@Test
	public void testConstructorWithOneParam() {
		double max = 10;
		GasTank gt = null;
		
		try {
			gt = new GasTank(max);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertTrue(gt instanceof GasTank);
		assertTrue(gt instanceof EnergySource);
		assertEquals((Double)max, (Double)gt.getMaxGasLevel());
		assertEquals((Double)max, (Double)gt.getGasLevel());
	}

	@Test
	public void testEmptyReturnsFalse() {
		double max = 10;
		double current = 0.1;
		GasTank gt = null;
		
		try {
			gt = new GasTank(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertTrue(!gt.empty());
	}

	@Test
	public void testEmptyReturnsTrue() {
		double max = 10;
		double current = 0;
		GasTank gt = null;
		
		try {
			gt = new GasTank(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertTrue(gt.empty());
	}
	
	@Test
	public void testFillUpWhenFilledUp() {
		double max = 10;
		GasTank gt = null;
		
		try {
			gt = new GasTank(max);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		gt.fillUp();
		
		assertEquals((Double)max, (Double)gt.getGasLevel());
	}

	@Test
	public void testFillUpWhenEmpty() {
		double max = 10;
		double current = 0;
		GasTank gt = null;
		
		try {
			gt = new GasTank(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		gt.fillUp();
		
		assertEquals((Double)max, (Double)gt.getGasLevel());
	}

	@Test
	public void testUse() {
		double max = 10;
		double current = 10;
		double cost = 10;
		GasTank gt = null;
		
		try {
			gt = new GasTank(max, current);
			
			gt.use(cost);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertEquals((Double)(current - cost), (Double)gt.getGasLevel());
	}

	@Test(expected = WrongEnegryUsageException.class)
	public void testUseWithNegativeParam() throws WrongEnegryUsageException{
		double max = 10;
		double current = 10;
		double cost = -2;
		GasTank gt = null;
		
		try {
			gt = new GasTank(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		try {
			gt.use(cost);
		} catch (OutOfEnergyException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
	}

	@Test(expected = OutOfEnergyException.class)
	public void testUseWithTooMuchCost() throws OutOfEnergyException{
		double max = 10;
		double current = 1.9;
		double cost = 2;
		GasTank gt = null;
		
		try {
			gt = new GasTank(max, current);			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		try {
			gt.use(cost);
		} catch (WrongEnegryUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
	}
	
	@Test
	public void testClone() {
		double max = 12;
		double current = 10;
		GasTank gt1 = null;
		
		try {
			gt1 = new GasTank(max, current);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		GasTank gt2 = gt1.clone();
		assertTrue(gt1 instanceof GasTank);
		assertTrue(gt2 instanceof GasTank);
		assertEquals((Double)max, (Double)gt1.getMaxGasLevel());
		assertEquals((Double)max, (Double)gt2.getMaxGasLevel());
		assertEquals((Double)current, (Double)gt1.getGasLevel());
		assertEquals((Double)current, (Double)gt2.getGasLevel());
	}

}
