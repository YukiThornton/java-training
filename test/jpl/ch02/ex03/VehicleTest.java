package jpl.ch02.ex03;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class VehicleTest {

	private static Vehicle vehicle1;
	private static Vehicle vehicle2;

	private static final long VEHICLE_1_ID = 1;
	private static final long VEHICLE_2_ID = 2;

	@BeforeClass
	public static void doBeforeClass() {
		vehicle1 = new Vehicle();
		vehicle2 = new Vehicle();
	}

	@Test
	public void testPublicFields() {
		double speed = 3.5;
		double direction = 76.3;
		String owner = "Tester";
		
		vehicle1.speed = speed;
		vehicle1.direction = direction;
		vehicle1.owner = owner;
		
		assertEquals((Double)speed, (Double)vehicle1.speed);
		assertEquals((Double)direction, (Double)vehicle1.direction);
		assertEquals(owner, vehicle1.owner);
		
	}
	
	@Test
	public void testVehicleId() {
		assertEquals((Long)VEHICLE_1_ID, (Long)vehicle1.id);
		assertEquals((Long)VEHICLE_2_ID, (Long)vehicle2.id);
		
	}
}
