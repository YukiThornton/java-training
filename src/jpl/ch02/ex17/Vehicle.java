package jpl.ch02.ex17;

public class Vehicle {
	
	// ++++++ fields ++++++
	private static long nextId = 1;
	public static final String TURN_LEFT = "TURN_LEFT";
	public static final String TURN_RIGHT = "TURN_RIGHT";
	
	private final long ID;
	private double speed;
	private double direction;
	private String owner;
	

	// ++++++ constructors ++++++
	public Vehicle() {
		ID = nextId++;		
	}
	
	public Vehicle(String owner) {
		this();
		this.owner = owner;
	}
	
	
	// ++++++ accessor methods ++++++
	public static long getNextId() {
		return nextId;
	}
	
	public long getId() {
		return ID;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getDirection() {
		return direction;
	}
	
	public void setDirection(double direction) {
		this.direction = direction;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}

	
	// ++++++ other methods ++++++
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

		System.out.println("The largest ID is " + getMaxId()); 
	}
	
	public String toString() {
		String desc = "Vehicle No." + this.ID + ": ";
		desc += "speed=" + this.speed + "km/h, "; 
		desc += "direction=" + this.direction + ", ";
		desc += "owner=" + this.owner;
		return desc;
	}
	
	public void changeSpeed (double speed) {
		setSpeed(speed);
	}
	
	public void stop() {
		setSpeed(0);
	}
	
	public void turn(double rotation) {
		direction += rotation;
	}
	
	public void turn(String rotation) {
		if (rotation.equals(TURN_LEFT)) {
			direction -= 90;
		} else if (rotation.equals(TURN_RIGHT)) {
			direction += 90;
		} else {
			System.out.println("Use TURN_LEFT or TURN_RIGHT.");
		}
	}

}
