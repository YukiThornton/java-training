package jpl.ch06.ex02;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class VehicleTest {

	private static Vehicle vehicle1;
	private static Vehicle vehicle2;
	
	private static final long VEHICLE_1_ID = 1;
	private static final double VEHICLE_1_SPEED = 3.5;
	private static final double VEHICLE_1_DIRECTION = 76.3;
	private static final String VEHICLE_1_OWNER = "Tester1";	
	private static final long VEHICLE_2_ID = 2;
	private static final double VEHICLE_2_SPEED = 41.1;
	private static final String VEHICLE_2_OWNER = "Tester2";

	@Test
	public void doTestsInOrder() {
		testConstructorWithNoParam();
		testConstructorWithOwnerParam();
		testSettersAndGetters();
		testGetMaxId();
		testGetFastestVehicle();
		testToString();
		testChangeSpeed();
		testStop();
		testTurnWithDoubleParam();
		testTurnWithFixedStringParam();
		testMain();
	}

	private void testConstructorWithNoParam() {
		vehicle1 = new Vehicle();
		assertEquals(null, vehicle1.getOwner());		
	}

	private void testConstructorWithOwnerParam() {
		vehicle2 = new Vehicle(VEHICLE_2_OWNER);
		assertEquals(VEHICLE_2_OWNER, vehicle2.getOwner());		
	}

	private void testSettersAndGetters() {
		testGetNextId();
		testGetId();
		testSetAndGetSpeed();
		testSetAndGetDirection();
		testSetAndGetOwner();
	}
	
	private void testGetNextId() {
		long nextId = 3;
		assertEquals((Long)nextId, (Long)Vehicle.getNextId());
	}

	private void testGetId() {
		assertEquals((Long)VEHICLE_1_ID, (Long)vehicle1.getId());
		assertEquals((Long)VEHICLE_2_ID, (Long)vehicle2.getId());
		
	}

	private void testSetAndGetSpeed() {
		vehicle1.setSpeed(VEHICLE_1_SPEED);
		assertEquals((Double)VEHICLE_1_SPEED, (Double)vehicle1.getSpeed());
	}

	private void testSetAndGetDirection() {
		vehicle1.setDirection(VEHICLE_1_DIRECTION);
		assertEquals((Double)VEHICLE_1_DIRECTION, (Double)vehicle1.getDirection());
	}

	private void testSetAndGetOwner() {
		vehicle1.setOwner(VEHICLE_1_OWNER);
		assertEquals(VEHICLE_1_OWNER, vehicle1.getOwner());
	}

	private void testGetMaxId() {
		assertEquals((Long)VEHICLE_2_ID, (Long)Vehicle.getMaxId());		
	}

	private void testGetFastestVehicle() {
		vehicle2.setSpeed(VEHICLE_2_SPEED);
		assertEquals(vehicle2, Vehicle.getFastestVehicle(vehicle1, vehicle2));
	}
	
	private void testToString() {
		assertThat(vehicle1.toString(), is("Vehicle No." + VEHICLE_1_ID 
				+ ": speed=" + VEHICLE_1_SPEED + "km/h, direction=" + VEHICLE_1_DIRECTION 
				+ ", owner=" + VEHICLE_1_OWNER));
	}
	
	private void testChangeSpeed() {
		double targetSpeed = vehicle1.getSpeed() + 10;
		vehicle1.changeSpeed(targetSpeed);
		assertEquals((Double)targetSpeed, (Double)vehicle1.getSpeed());		
	}
	
	private void testStop() {
		double targetSpeed = 0;
		vehicle1.stop();
		assertEquals((Double)targetSpeed, (Double)vehicle1.getSpeed());		
	}
	
	private void testTurnWithDoubleParam() {
		double diff = 10;
		double targetDirection = vehicle1.getDirection() + diff;
		vehicle1.turn(diff);
		assertEquals((Double)targetDirection, (Double)vehicle1.getDirection());		
	}
	
	private void testTurnWithFixedStringParam() {
		double targetDirectionToRight = vehicle1.getDirection() + 90;
		vehicle1.turn(Vehicle.Instruction.TURN_RIGHT);
		assertEquals((Double)targetDirectionToRight, (Double)vehicle1.getDirection());		

		double targetDirectionToLeft = vehicle1.getDirection() - 90;
		vehicle1.turn(Vehicle.Instruction.TURN_LEFT);
		assertEquals((Double)targetDirectionToLeft, (Double)vehicle1.getDirection());		
}
	
	private void testMain() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		Vehicle.main(new String[0]);
		
		assertThat(out.toString(), is("Vehicle No.3: speed=60.0km/h, direction=30.0, owner=Satoh" + System.lineSeparator()
			+ "Vehicle No.4: speed=55.5km/h, direction=13.0, owner=Suzuki" + System.lineSeparator() 
			+ "Vehicle No.5: speed=77.0km/h, direction=-9.0, owner=Katoh" + System.lineSeparator()
				));
	}
}
