package jpl.ch04.ex01;

public class Vehicle {
	
	// ++++++ fields ++++++
	private static long nextId = 1;
	public static final String TURN_LEFT = "TURN_LEFT";
	public static final String TURN_RIGHT = "TURN_RIGHT";
	
	private final long ID;
	private double speed;
	private double direction;
	private String owner;
	private final EnergySource[] energySources;
	

	// ++++++ constructors ++++++
	public Vehicle(EnergySource[] energySources) throws WrongVehicleUsageException{
		ID = nextId++;
		if (energySources == null || energySources.length == 0) {
			throw new WrongVehicleUsageException("Set valid energy sources");
		}
		this.energySources = energySources;
	}
	
	public Vehicle(EnergySource[] energySources, String owner) throws WrongVehicleUsageException {
		this(energySources);
		this.owner = owner;
	}
	
	
	// ++++++ accessor methods ++++++
	public static final long getNextId() {
		return nextId;
	}
	
	// CAUTION! This method is only for testing purpose.
	protected static final void setNextId(long nextId) {
		Vehicle.nextId = nextId;
	}
	
	public final long getId() {
		return ID;
	}
	
	public final double getSpeed() {
		return speed;
	}
	
	public final void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public final double getDirection() {
		return direction;
	}
	
	public final void setDirection(double direction) {
		this.direction = direction;
	}
	
	public final String getOwner() {
		return owner;
	}
	
	public final void setOwner(String owner) {
		this.owner = owner;
	}

	public final EnergySource[] getEnergySources() {
		return energySources;
	}

	
	// ++++++ other methods ++++++
	public static long getMaxId() {
		return nextId - 1;
	}

	public static void main (String[] args) {
		try {
			EnergySource[] energySources = new EnergySource[2];
			energySources[0] = new GasTank(20, 20);
			energySources[1] = new Battery(10, 10);
			Vehicle vehicleA = new Vehicle(energySources, args[0]);			
			vehicleA.speed= 60.0;
			vehicleA.direction = 30.0;
					
			System.out.println(vehicleA);
		} catch (WrongVehicleUsageException | WrongEnegryUsageException e) {
			e.printStackTrace();
		}
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
	
	/**
	 * This methods starts the Vehicle instance.
	 * It makes sure that this vehicle is not started yet, 
	 * and that it has enough energy,
	 * and then it sets target speed on speed.
	 * 
	 * @param targetSpeed
	 * @throws WrongVehicleUsageException
	 * @throws OutOfEnergyException
	 */
	public void start(double targetSpeed) throws WrongVehicleUsageException, OutOfEnergyException{
		// Throws an exception when the speed is not 0 or when targetSpeed is less than 0.
		if (this.getSpeed() != 0) {
			throw new WrongVehicleUsageException("Start the vehicle after the vehicle stops.");
		}
		// Throws an exception when targetSpeed is less than 0.
		if (targetSpeed <= 0) {
			throw new WrongVehicleUsageException("Invalid parameter.");
		}
		// Throws an exception when energy source is empty.
		if (!hasEnergyLeft()) {
			throw new OutOfEnergyException("No energy.");			
		}
		this.setSpeed(targetSpeed);
	}
	
	/**
	 * Checks all the energy sources to see if this vehicle has some energy left.
	 * @return true when this vehicle has some energy left
	 */
	protected boolean hasEnergyLeft() {
		int count = 0;
		for (int i = 0; i < energySources.length; i++) {
			if (energySources[i].empty()) {
				count++;
			}			
		}
		return (count < energySources.length);
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
