package jpl.ch03.ex09;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Test;

import jpl.ch03.ex08.Battery;
import jpl.ch03.ex08.EnergySource;
import jpl.ch03.ex08.EnergySource.WrongEnegryUsageException;
import jpl.ch03.ex08.GasTank;
import jpl.ch03.ex08.Vehicle;
import jpl.ch03.ex08.Vehicle.WrongVehicleUsageException;

public class GarageTest {

	@After
	public void doAfterEachTest() {
		Vehicle.setNextId(1);
	}
	
	@Test
	public void testConstructorWithNoParam() {
		Garage garage = new Garage();
		
		assertTrue(garage instanceof Garage);
	}

	@Test
	public void testConstructorWithOneParam() {
		EnergySource[] energySources = null;
		Vehicle[] vehicles = new Vehicle[1];
		try {
			energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicles[0] = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		Garage garage = new Garage(vehicles);
		
		assertTrue(garage instanceof Garage);
		assertEquals(vehicles[0], garage.getVehicles()[0]);
	}

	@Test
	public void testVehiclesSetterAndGetter() {
		EnergySource[] energySources = null;
		Vehicle[] vehicles = new Vehicle[1];
		try {
			energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			
			vehicles[0] = new Vehicle(energySources);			

		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		Garage garage = new Garage();
		garage.setVehicles(vehicles);
		
		assertTrue(garage instanceof Garage);
		assertEquals(vehicles[0], garage.getVehicles()[0]);
	}

	@Test
	public void testClone() {
		Vehicle[] vehicles = new Vehicle[2];
		try {
			EnergySource[] eSources1 = new EnergySource[2];
			eSources1[0] = (EnergySource)new GasTank(60);
			eSources1[1] = new Battery(90);
			vehicles[0] = new Vehicle(eSources1, "main1");
			
			EnergySource[] eSources2 = new EnergySource[2];
			eSources2[0] = new GasTank(18);
			eSources2[1] = new Battery(98);
			vehicles[1] = new Vehicle(eSources2, "main2");
			
		} catch(WrongEnegryUsageException | WrongVehicleUsageException e) {
			e.printStackTrace();
			fail("Caught an unexpected exception.");
		}
		Garage garage = new Garage(vehicles);
		
		Garage garageCloned = garage.clone();
		
		assertTrue(garage.getVehicles().length == garageCloned.getVehicles().length);
		assertTrue(garage.getVehicles()[0].getSpeed() == garageCloned.getVehicles()[0].getSpeed());
		assertTrue(garage.getVehicles()[0].getDirection() == garageCloned.getVehicles()[0].getDirection());
		assertTrue(garage.getVehicles()[0].getOwner().equals(garageCloned.getVehicles()[0].getOwner()));
		assertTrue(garage.getVehicles()[0].getEnergySources().length == garageCloned.getVehicles()[0].getEnergySources().length);
		assertTrue(garage.getVehicles()[0].getEnergySources()[0].equals(garageCloned.getVehicles()[0].getEnergySources()[0]));
		assertTrue(garage.getVehicles()[0].getEnergySources()[1].equals(garageCloned.getVehicles()[0].getEnergySources()[1]));
	}

	@Test
	public void testMain() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		Garage.main(new String[0]);
		
		assertThat(out.toString(), is("#1 General garage comparison"  + System.lineSeparator() 
		+ "Garage0: 2 vehicles" + System.lineSeparator()
		+ "Vehicle No.1" + System.lineSeparator() 
		+ "Vehicle No.2" + System.lineSeparator() 
		+ "Garage1: 2 vehicles" + System.lineSeparator() 
		+ "Vehicle No.3" + System.lineSeparator() 
		+ "Vehicle No.4" + System.lineSeparator() + System.lineSeparator()
		+ "#2 Vehicle comparison" + System.lineSeparator()
		+ "All the vehicles in each garage are same: true" + System.lineSeparator()));
	}

}
