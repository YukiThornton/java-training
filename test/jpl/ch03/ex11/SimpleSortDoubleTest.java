package jpl.ch03.ex11;

import static org.junit.Assert.*;

import org.junit.Test;

import jpl.ch03.ex11.SortDouble.SortMetrics;

public class SimpleSortDoubleTest {

	@Test
	public void testConstructor() {
		
		SimpleSortDouble simpleSortDouble = new SimpleSortDouble();
		
		assertTrue(simpleSortDouble instanceof SimpleSortDouble);
		assertTrue(simpleSortDouble instanceof SortDouble);
	}
	
	@Test
	public void testGetMetrics() {
		
		SimpleSortDouble simpleSortDouble = new SimpleSortDouble();
		
		SortMetrics sortMetrics = simpleSortDouble.getMetrics();
		
		assertTrue(sortMetrics instanceof SortMetrics);
		assertTrue(sortMetrics.getProbeCnt() == 0);
		assertTrue(sortMetrics.getCompareCnt() == 0);
		assertTrue(sortMetrics.getSwapCnt() == 0);
	}
	
	@Test
	public void testSort() {
		
		SimpleSortDouble simpleSortDouble = new SimpleSortDouble();
		double first =  0.9;
		double second = 1.1;
		double third = 1.4;
		double[] data = {second, third, first};
		
		SortMetrics sortMetrics = simpleSortDouble.sort(data);
		
		assertTrue(sortMetrics.getProbeCnt() == 0);
		assertTrue(sortMetrics.getCompareCnt() == 3);
		assertTrue(sortMetrics.getSwapCnt() == 2);
		assertTrue(data[0] == first);
		assertTrue(data[1] == second);
		assertTrue(data[2] == third);
	}

	@Test
	public void testSortWithEmptyArray() {
		
		SimpleSortDouble simpleSortDouble = new SimpleSortDouble();
		double[] data = new double[0];
		
		SortMetrics sortMetrics = simpleSortDouble.sort(data);
		
		assertTrue(sortMetrics.getProbeCnt() == 0);
		assertTrue(sortMetrics.getCompareCnt() == 0);
		assertTrue(sortMetrics.getSwapCnt() == 0);
	}


	@Test(expected = NullPointerException.class)
	public void testSortWithNullArray() {
		
		SimpleSortDouble simpleSortDouble = new SimpleSortDouble();
		double[] data = null;
		
		simpleSortDouble.sort(data);
	}
}
