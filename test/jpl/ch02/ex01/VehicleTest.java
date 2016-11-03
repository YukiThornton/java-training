package jpl.ch02.ex01;

import static org.junit.Assert.*;

import org.junit.Test;

public class VehicleTest {
	
	@Test
	public void testPublicFields() {
		double speed = 3.5;
		double direction = 76.3;
		String owner = "Tester";
		
		Vehicle vehicle = new Vehicle();
		
		vehicle.speed = speed;
		vehicle.direction = direction;
		vehicle.owner = owner;
		
		assertEquals((Double)speed, (Double)vehicle.speed);
		assertEquals((Double)direction, (Double)vehicle.direction);
		assertEquals(owner, vehicle.owner);
	}

}
