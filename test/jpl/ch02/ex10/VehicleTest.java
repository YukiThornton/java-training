package jpl.ch02.ex10;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

//アルファベット順にテストは実行される。
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleTest {

	private static Vehicle vehicle1;
	private static Vehicle vehicle2;
	
	private static final long VEHICLE_1_ID = 1;
	private static final double VEHICLE_1_SPEED = 3.5;
	private static final double VEHICLE_1_DIRECTION = 76.3;
	private static final String VEHICLE_1_OWNER = "Tester1";	
	private static final long VEHICLE_2_ID = 2;
	private static final String VEHICLE_2_OWNER = "Tester2";

	@BeforeClass
	public static void doBeforeClass() {
		vehicle1 = new Vehicle();
		vehicle2 = new Vehicle(VEHICLE_2_OWNER);
	}

	@Test
	public void testConstructorWithOwnerParam() {
		assertEquals(VEHICLE_2_OWNER, vehicle2.owner);
		
	}

	@Test
	public void testGetMaxId() {
		assertEquals((Long)VEHICLE_2_ID, (Long)Vehicle.getMaxId());		
	}

	@Test
	public void testMain() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		Vehicle.main(new String[0]);
		
		assertThat(out.toString(), is("Vehicle No.3: speed=60.0km/h, direction=30.0, owner=Satoh" + System.lineSeparator()
			+ "Vehicle No.4: speed=55.5km/h, direction=13.0, owner=Suzuki" + System.lineSeparator() 
			+ "Vehicle No.5: speed=77.0km/h, direction=-9.0, owner=Katoh" + System.lineSeparator()
				));
	}
	
	@Test
	public void testPublicFields() {
		vehicle1.speed = VEHICLE_1_SPEED;
		vehicle1.direction = VEHICLE_1_DIRECTION;
		vehicle1.owner = VEHICLE_1_OWNER;
		
		assertEquals((Double)VEHICLE_1_SPEED, (Double)vehicle1.speed);
		assertEquals((Double)VEHICLE_1_DIRECTION, (Double)vehicle1.direction);
		assertEquals(VEHICLE_1_OWNER, vehicle1.owner);
		
	}

	@Test
	public void testToString() {
		assertThat(vehicle1.toString(), is("Vehicle No." + VEHICLE_1_ID 
				+ ": speed=" + VEHICLE_1_SPEED + "km/h, direction=" + VEHICLE_1_DIRECTION 
				+ ", owner=" + VEHICLE_1_OWNER));
	}
	
	@Test
	public void testVehicleId() {
		assertEquals((Long)VEHICLE_1_ID, (Long)vehicle1.ID);
		assertEquals((Long)VEHICLE_2_ID, (Long)vehicle2.ID);
		
	}

}
