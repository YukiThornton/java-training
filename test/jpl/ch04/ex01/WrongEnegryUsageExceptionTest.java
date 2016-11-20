package jpl.ch04.ex01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class WrongEnegryUsageExceptionTest {

	@Test
	public void testConstructor() {
		String message = "message";
		
		WrongEnegryUsageException wrongEnegryUsageException = new WrongEnegryUsageException(message);
		
		assertTrue(wrongEnegryUsageException instanceof WrongEnegryUsageException);
		assertTrue(wrongEnegryUsageException instanceof Exception);
		assertThat(wrongEnegryUsageException.getMessage(), is(message));
	}

}
