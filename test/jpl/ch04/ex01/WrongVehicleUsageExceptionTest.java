package jpl.ch04.ex01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class WrongVehicleUsageExceptionTest {

	@Test
	public void testConstructor() {
		String message = "message";
		
		WrongVehicleUsageException wrongVehicleUsageException = new WrongVehicleUsageException(message);
		
		assertTrue(wrongVehicleUsageException instanceof WrongVehicleUsageException);
		assertTrue(wrongVehicleUsageException instanceof Exception);
		assertThat(wrongVehicleUsageException.getMessage(), is(message));
	}

}
