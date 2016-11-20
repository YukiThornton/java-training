package jpl.ch04.ex02;

import static org.junit.Assert.*;

import org.junit.Test;

import jpl.ch04.ex02.SortHarness.SortMetrics;

public class SortIntegerTest {

	@Test
	public void testConstructor() {
		
		SortInteger sortInteger = new SortInteger();
		
		assertTrue(sortInteger instanceof SortInteger);
		assertTrue(sortInteger instanceof SortHarness);
		assertTrue(sortInteger instanceof ComparableObject);
	}
	
	@Test
	public void testGetMetrics() {
		
		SortInteger sortInteger = new SortInteger();
		
		SortMetrics sortMetrics = sortInteger.getMetrics();
		
		assertTrue(sortMetrics instanceof SortMetrics);
		assertTrue(sortMetrics.getProbeCnt() == 0);
		assertTrue(sortMetrics.getCompareCnt() == 0);
		assertTrue(sortMetrics.getSwapCnt() == 0);
	}
	
	@Test
	public void testSort() {
		
		SortInteger sortInteger = new SortInteger();
		Integer first = new Integer(1);
		Integer second = new Integer(2);
		Integer third = new Integer(3);
		Integer[] data = {second, third, first};
		
		SortMetrics sortMetrics = sortInteger.sort(data);
		
		assertTrue(sortMetrics.getProbeCnt() == 0);
		assertTrue(sortMetrics.getCompareCnt() == 3);
		assertTrue(sortMetrics.getSwapCnt() == 2);
		assertTrue(data[0] == first);
		assertTrue(data[1] == second);
		assertTrue(data[2] == third);
	}

	@Test
	public void testSortWithSameValuesInData() {
		
		SortInteger sortInteger = new SortInteger();
		Integer first = new Integer(1);
		Integer second = new Integer(2);
		Integer fisrt2 = new Integer(1);
		Integer[] data = {first, fisrt2, second};
		
		SortMetrics sortMetrics = sortInteger.sort(data);
		
		assertTrue(sortMetrics.getProbeCnt() == 0);
		assertTrue(sortMetrics.getCompareCnt() == 3);
		assertTrue(sortMetrics.getSwapCnt() == 0);
		assertTrue(data[0] == first);
		assertTrue(data[1] == fisrt2);
		assertTrue(data[2] == second);
	}

	@Test
	public void testSortWithEmptyArray() {
		
		SortInteger sortInteger = new SortInteger();
		Integer[] data = new Integer[0];
		
		SortMetrics sortMetrics = sortInteger.sort(data);
		
		assertTrue(sortMetrics.getProbeCnt() == 0);
		assertTrue(sortMetrics.getCompareCnt() == 0);
		assertTrue(sortMetrics.getSwapCnt() == 0);
	}

	@Test(expected = NullPointerException.class)
	public void testSortWithNullArray() {
		
		SortInteger sortInteger = new SortInteger();
		Integer[] data = null;
		
		sortInteger.sort(data);
	}

}
