package jpl.ch02.ex12;


/*
 * Answer: 
 * 複数のVehicleオブジェクトを引数に取ってそれらを評価するようなstaticメソッドであれば
 * 可変長引数メソッドが必要である。
 * 例として、以下にlinkedFromメソッドを実装した。
 */

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
	
	/**
	 * 与えられた引数の中で、一番大きなspeedを持つVehicleオブジェクトを返すメソッド.
	 * 同じspeedを持つVehicleオブジェクトが複数あり、かつそれが最大のspeedである場合、
	 * 引数の中で一番左側に宣言されたものが返る。
	 * @param vehicles
	 * @return 一番大きなspeedを持つVehicleオブジェクト
	 */
	public static Vehicle getFastestVehicle(Vehicle... vehicles) {
		Vehicle fastestVehicle = vehicles[0];
		for (int i = 1; i < vehicles.length; i++) {
			if (fastestVehicle.speed < vehicles[i].speed) {
				fastestVehicle = vehicles[i];
			}
		}
		return fastestVehicle;
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
		System.out.println("The fastest car is " + getFastestVehicle(vehicleA, vehicleB, vehicleC)); 
	}
	
	public String toString() {
		String desc = "Vehicle No." + this.ID + ": ";
		desc += "speed=" + this.speed + "km/h, "; 
		desc += "direction=" + this.direction + ", ";
		desc += "owner=" + this.owner;
		return desc;
	}

}
