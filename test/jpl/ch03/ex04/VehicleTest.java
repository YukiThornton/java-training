package jpl.ch03.ex04;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Test;

public class VehicleTest {

	@After
	public void doAfterEachTest() {
		Vehicle.setNextId(1);
	}
	
	@Test
	public void testConstructorWithNoParam() {
		Vehicle vehicle = new Vehicle();
		assertEquals(null, vehicle.getOwner());		
	}

	@Test
	public void testConstructorWithOwnerParam() {
		String vehicleOwner = "Tester1";	
		Vehicle vehicle = new Vehicle(vehicleOwner);
		assertEquals(vehicleOwner, vehicle.getOwner());		
	}

	@Test
	public void testGetNextId() {
		long nextId = 1;
		assertEquals((Long)nextId, (Long)Vehicle.getNextId());
	}

	@Test
	public void testGetId() {
		long vehicleId = 1;
		Vehicle vehicle = new Vehicle();
		
		assertEquals((Long)vehicleId, (Long)vehicle.getId());
		
	}

	@Test
	public void testSetAndGetSpeed() {
		double vehicleSpeed = 10;
		Vehicle vehicle = new Vehicle();
		
		vehicle.setSpeed(vehicleSpeed);
		assertEquals((Double)vehicleSpeed, (Double)vehicle.getSpeed());
	}

	@Test
	public void testSetAndGetDirection() {
		double vehicleDirection = 10;
		Vehicle vehicle = new Vehicle();
		
		vehicle.setDirection(vehicleDirection);
		assertEquals((Double)vehicleDirection, (Double)vehicle.getDirection());
	}

	@Test
	public void testSetAndGetOwner() {
		String vehicleOwner = "Tester1";
		Vehicle vehicle = new Vehicle();
		
		vehicle.setOwner(vehicleOwner);
		assertEquals(vehicleOwner, vehicle.getOwner());
	}

	@Test
	public void testGetMaxId() {
		long maxId = 1;
		new Vehicle();
		assertEquals((Long)maxId, (Long)Vehicle.getMaxId());		
	}

	@Test
	public void testToString() {
		long vehicleId = 1;
		double vehicleSpeed = 0.0;
		double vehicleDirection = 0.0;
		String vehicleOwner = null;
		Vehicle vehicle = new Vehicle();
		
		assertThat(vehicle.toString(), is("Vehicle No." + vehicleId 
				+ ": speed=" + vehicleSpeed + "km/h, direction=" + vehicleDirection
				+ ", owner=" + vehicleOwner));
	}
	
	@Test
	public void testChangeSpeed() {
		double vehicleSpeed = 10;
		Vehicle vehicle = new Vehicle();
		
		vehicle.changeSpeed(vehicleSpeed);
		assertEquals((Double)vehicleSpeed, (Double)vehicle.getSpeed());		
	}
	
	@Test
	public void testStop() {
		double vehicleSpeed = 0;
		Vehicle vehicle = new Vehicle();
		
		vehicle.stop();
		assertEquals((Double)vehicleSpeed, (Double)vehicle.getSpeed());		
	}
	
	@Test
	public void testTurnWithDoubleParam() {
		double vehicleDirection = 0;
		Vehicle vehicle = new Vehicle();
		vehicle.setDirection(vehicleDirection);
		double diff = 10;
		double targetDirection = vehicleDirection + diff;
		
		vehicle.turn(diff);
		assertEquals((Double)targetDirection, (Double)vehicle.getDirection());		
	}
	
	@Test
	public void testTurnWithFixedStringParam() {
		double vehicleDirection = 0;
		Vehicle vehicle = new Vehicle();
		vehicle.setDirection(vehicleDirection);
		double targetDirectionToRight = vehicle.getDirection() + 90;
		vehicle.turn(Vehicle.TURN_RIGHT);
		assertEquals((Double)targetDirectionToRight, (Double)vehicle.getDirection());		

		double targetDirectionToLeft = vehicle.getDirection() - 90;
		vehicle.turn(Vehicle.TURN_LEFT);
		assertEquals((Double)targetDirectionToLeft, (Double)vehicle.getDirection());		
}
	
	@Test
	public void testMain() {
		String[] args = new String[1];
		args[0] = "Kuma";
		
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		Vehicle.main(args);
		
		assertThat(out.toString(), is("Vehicle No.1: speed=60.0km/h, direction=30.0, owner=" + args[0] + System.lineSeparator()));
	}

}
