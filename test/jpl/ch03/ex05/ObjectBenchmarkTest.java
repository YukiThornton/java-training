package jpl.ch03.ex05;

import static org.junit.Assert.*;

import org.junit.Test;

public class ObjectBenchmarkTest {

	@Test
	public void testConstructor() {
		ObjectBenchmark objectBenchmark = new ObjectBenchmark();
		
		assertTrue(objectBenchmark instanceof Benchmark);
		assertTrue(objectBenchmark instanceof ObjectBenchmark);
	}
	
	@Test
	public void testRepeatWithPositiveNum() {
		ObjectBenchmark objectBenchmark = new ObjectBenchmark();
		
		try {
			long result = objectBenchmark.repeat(1);
		} catch (Exception e) {
			fail("Exception!");
		}
	}
	
	@Test
	public void testRepeatWithZero() {
		ObjectBenchmark objectBenchmark = new ObjectBenchmark();
		
		try {
			long result = objectBenchmark.repeat(0);
		} catch (Exception e) {
			fail("Exception!");
		}
	}
	
	@Test
	public void testRepeatWithNegativeNum() {
		ObjectBenchmark objectBenchmark = new ObjectBenchmark();
		
		try {
			long result = objectBenchmark.repeat(-1);
		} catch (Exception e) {
			fail("Exception!");
		}		
	}

	@Test
	public void testBenchmark() {
		ObjectBenchmark objectBenchmark = new ObjectBenchmark();
		
		try {
			objectBenchmark.benchmark();
		} catch (Exception e) {
			fail("Exception!");
		}		
	}

	@Test
	public void testMain() {
		String[] args = new String[1];
		args[0] = "99";
		try {
			ObjectBenchmark.main(args);
		} catch (Exception e) {
			fail("Exception!");
		}		
	}

}
