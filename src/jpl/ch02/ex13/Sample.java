package jpl.ch02.ex13;

public class Sample {

	public static void main(String[] args) {
		Vehicle vehicleA = new Vehicle();
		
		System.out.println("設定前");
		System.out.println("Vehicle ID: " + vehicleA.getId());
		System.out.println("speed: " + vehicleA.getSpeed());
		System.out.println("direction: " + vehicleA.getDirection());
		System.out.println("owner: " + vehicleA.getOwner());
		vehicleA.setSpeed(98.5);
		vehicleA.setDirection(9.8);
		vehicleA.setOwner("Lisa");
		System.out.println("設定後");
		System.out.println("Vehicle ID: " + vehicleA.getId());
		System.out.println("speed: " + vehicleA.getSpeed());
		System.out.println("direction: " + vehicleA.getDirection());
		System.out.println("owner: " + vehicleA.getOwner());
		
		System.out.println("次のVehicleID: " + Vehicle.getNextId());

	}

}
