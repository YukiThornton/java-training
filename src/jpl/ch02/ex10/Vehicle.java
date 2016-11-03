package jpl.ch02.ex10;

public class Vehicle {
	private static long nextId = 1;
	
	public final long ID;
	public double speed;
	public double direction;
	public String owner;
	
	public Vehicle() {
		ID = nextId++;		
	}
	
	public Vehicle(String owner) {
		this();
		this.owner = owner;
	}
	
	public static long getMaxId() {
		return nextId - 1;
	}
	
	public static void main (String[] args) {
		Vehicle vehicleA = new Vehicle();
		vehicleA.speed= 60.0;
		vehicleA.direction = 30.0;
		vehicleA.owner = "Satoh";
		
		Vehicle vehicleB = new Vehicle("Suzuki");
		vehicleB.speed= 55.5;
		vehicleB.direction = 13.0;
		
		Vehicle vehicleC = new Vehicle();
		vehicleC.speed= 77.0;
		vehicleC.direction = -9.0;
		vehicleC.owner = "Katoh";
		
		System.out.println(vehicleA);
		System.out.println(vehicleB);
		System.out.println(vehicleC);
	}

	public String toString() {
		String desc = "Vehicle No." + this.ID + ": ";
		desc += "speed=" + this.speed + "km/h, "; 
		desc += "direction=" + this.direction + ", ";
		desc += "owner=" + this.owner;
		return desc;
	}

}
