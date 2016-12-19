package jpl.ch07.ex03;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class PascalsTriangleTest {

	@Test
	public void testCreateWithVariousDepth() throws Exception {
		int rowNumMin = 1;
		int[][] triangleMin = PascalsTriangle.create(rowNumMin);
		testBasic(triangleMin, rowNumMin);

		int rowNumSmall = 5;
		int[][] triangleSmall = PascalsTriangle.create(rowNumSmall);
		testBasic(triangleSmall, rowNumSmall);

		int rowNumMedium = 12;
		int[][] triangleMedium = PascalsTriangle.create(rowNumMedium);
		testBasic(triangleMedium, rowNumMedium);

		int rowNumLarge = 100;
		int[][] triangleLarge = PascalsTriangle.create(rowNumLarge);
		testBasic(triangleLarge, rowNumLarge);

	}
	
	@Test(expected=Exception.class)
	public void testCreateThrowsException() throws Exception{
		int rowNumMin = 0;
		PascalsTriangle.create(rowNumMin);
	}
	
	@Test
	public void testShowSuccess() throws Exception{
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		int rowNum = 3;
		int[][] triangle = PascalsTriangle.create(rowNum);
		PascalsTriangle.show(triangle);
		
		assertThat(out.toString(), is("  -1-" + System.lineSeparator() + 
									  " -1-1-" + System.lineSeparator() + 
									  "-1-2-1-" + System.lineSeparator()));
	}
	
	@Test(expected=Exception.class)
	public void testShowThrowsException() throws Exception{
		PascalsTriangle.show(new int[1][0]);
	}
	
	private void testBasic(int[][] triangle, int rowNum) {
		assertTrue(triangle != null);
		assertTrue(triangle.length == rowNum);

		for(int[] row: triangle) {
			assertTrue(row != null);			
			assertTrue(row.length >= 1);			
		}

		assertTrue(triangle[0][0] == 1);
		
		for(int j = 1; j < triangle.length - 1; j++) {
			assertTrue(triangle[j][0] == 1);
			assertTrue(triangle[j][triangle[j].length - 1] == 1);
			for(int i = 0; i < triangle[j].length - 1; i++) {
				assertTrue(triangle[j][i] + triangle[j][i + 1] == triangle[j + 1][i + 1]);
			}
		}
		
	}

}
