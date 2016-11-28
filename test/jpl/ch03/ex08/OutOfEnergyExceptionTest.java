package jpl.ch03.ex08;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;
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
