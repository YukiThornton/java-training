package jpl.ch04.ex01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class OutOfEnergyExceptionTest {

	@Test
	public void testConstructor() {
		String message = "message";
		
		OutOfEnergyException energyException = new OutOfEnergyException(message);
		
		assertTrue(energyException instanceof OutOfEnergyException);
		assertTrue(energyException instanceof Exception);
		assertThat(energyException.getMessage(), is(message));
	}

}
