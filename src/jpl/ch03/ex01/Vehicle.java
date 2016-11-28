package jpl.ch03.ex01;

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
	
	// CAUTION! This method is only for testing purpose.
	protected static void setNextId(long nextId) {
		Vehicle.nextId = nextId;
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
		Vehicle vehicleA = new Vehicle(args[0]);
		vehicleA.speed= 60.0;
		vehicleA.direction = 30.0;
				
		System.out.println(vehicleA);
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
