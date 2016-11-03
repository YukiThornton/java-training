package jpl.ch02.ex09;

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
		
		
		System.out.println("Vehicle No." 
				+ vehicleA.ID + ": speed=" 
				+ vehicleA.speed + "km/h, direction=" 
				+ vehicleA.direction + ", owner=" 
				+ vehicleA.owner);
		System.out.println("Vehicle No." 
				+ vehicleB.ID + ": speed=" 
				+ vehicleB.speed + "km/h, direction=" 
				+ vehicleB.direction + ", owner=" 
				+ vehicleB.owner);
		System.out.println("Vehicle No." 
				+ vehicleC.ID + ": speed=" 
				+ vehicleC.speed + "km/h, direction=" 
				+ vehicleC.direction + ", owner=" 
				+ vehicleC.owner);
	}

}
