package jpl.ch04.ex01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Test;

public class VehicleTest {

	@After
	public void doAfterEachTest() {
		Vehicle.setNextId(1);
	}
	
	@Test
	public void testConstructorWithOneParam() {
		EnergySource[] energySources = null;
		try {
			energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
		} catch(WrongEnegryUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		Vehicle vehicle = null;
		try {
			vehicle = new Vehicle(energySources);			
		} catch(WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		assertTrue(energySources.equals(vehicle.getEnergySources()));
		assertEquals(null, vehicle.getOwner());		
	}

	@Test(expected = WrongVehicleUsageException.class)
	public void testConstructorWithOneParamNullValue() throws WrongVehicleUsageException {
		EnergySource[] energySources = null;
		new Vehicle(energySources);
	}

	@Test(expected = WrongVehicleUsageException.class)
	public void testConstructorWithOneParamTooShortArray() throws WrongVehicleUsageException {
		EnergySource[] energySources = null;
		energySources = new EnergySource[0];
		new Vehicle(energySources);
	}

	@Test
	public void testConstructorWithTwoParams() {
		String vehicleOwner = "Tester1";
		EnergySource[] energySources = null;
		Vehicle vehicle = null;
		
		try {
			energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources, vehicleOwner);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertTrue(energySources.equals(vehicle.getEnergySources()));
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
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertEquals((Long)vehicleId, (Long)vehicle.getId());
		
	}

	@Test
	public void testSetAndGetSpeed() {
		double vehicleSpeed = 10;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
				
		vehicle.setSpeed(vehicleSpeed);
		assertEquals((Double)vehicleSpeed, (Double)vehicle.getSpeed());
	}

	@Test
	public void testSetAndGetDirection() {
		double vehicleDirection = 10;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
				
		vehicle.setDirection(vehicleDirection);
		assertEquals((Double)vehicleDirection, (Double)vehicle.getDirection());
	}

	@Test
	public void testSetAndGetOwner() {
		String vehicleOwner = "Tester1";
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
				
		vehicle.setOwner(vehicleOwner);
		assertEquals(vehicleOwner, vehicle.getOwner());
	}

	@Test
	public void testGetEnergySources() {
		EnergySource[] energySources = null;
		Vehicle vehicle = null;
		
		try {
			energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
				
		assertTrue(vehicle.getEnergySources().equals(energySources));
	}

	@Test
	public void testGetMaxId() {
		long maxId = 1;
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertEquals((Long)maxId, (Long)Vehicle.getMaxId());		
	}

	@Test
	public void testToString() {
		long vehicleId = 1;
		double vehicleSpeed = 0.0;
		double vehicleDirection = 0.0;
		String vehicleOwner = null;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
				
		assertThat(vehicle.toString(), is("Vehicle No." + vehicleId 
				+ ": speed=" + vehicleSpeed + "km/h, direction=" + vehicleDirection
				+ ", owner=" + vehicleOwner));
	}
	
	@Test
	public void testChangeSpeed() {
		double vehicleSpeed = 10;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
				
		vehicle.changeSpeed(vehicleSpeed);
		assertEquals((Double)vehicleSpeed, (Double)vehicle.getSpeed());		
	}
	
	@Test
	public void testStart() {
		double targetSpeed = 10;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 0);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
				
		try {
			vehicle.start(targetSpeed);
		} catch (WrongVehicleUsageException | OutOfEnergyException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertEquals((Double)targetSpeed, (Double)vehicle.getSpeed());		
	}
	
	@Test(expected = WrongVehicleUsageException.class)
	public void testStartThrowsExceptionWhenVehicleAlreadyMoving() throws WrongVehicleUsageException {
		double vehicleSpeed = 50;
		double targetSpeed = 10;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 0);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		vehicle.setSpeed(vehicleSpeed);
				
		try {
			vehicle.start(targetSpeed);
		} catch (OutOfEnergyException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertEquals((Double)targetSpeed, (Double)vehicle.getSpeed());		
	}
	
	@Test(expected = WrongVehicleUsageException.class)
	public void testStartThrowsExceptionWhenTargetSpeedIsNegative() throws WrongVehicleUsageException {
		double targetSpeed = 0;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 0);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
				
		try {
			vehicle.start(targetSpeed);
		} catch (OutOfEnergyException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertEquals((Double)targetSpeed, (Double)vehicle.getSpeed());		
	}
	
	@Test(expected = OutOfEnergyException.class)
	public void testStartThrowsExceptionWhenEnergyIsInsufficient() throws OutOfEnergyException {
		double targetSpeed = 10;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 0);
			energySources[1] = new Battery(10, 0);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
				
		try {
			vehicle.start(targetSpeed);
		} catch (WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
		assertEquals((Double)targetSpeed, (Double)vehicle.getSpeed());		
	}
	
	@Test
	public void testStop() {
		double vehicleSpeed = 0;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
				
		vehicle.stop();
		assertEquals((Double)vehicleSpeed, (Double)vehicle.getSpeed());		
	}
	
	@Test
	public void testTurnWithDoubleParam() {
		double vehicleDirection = 0;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		vehicle.setDirection(vehicleDirection);
		double diff = 10;
		double targetDirection = vehicleDirection + diff;
		
		vehicle.turn(diff);
		assertEquals((Double)targetDirection, (Double)vehicle.getDirection());		
	}
	
	@Test
	public void testTurnWithFixedStringParam() {
		double vehicleDirection = 0;
		Vehicle vehicle = null;
		
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicle = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		
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
