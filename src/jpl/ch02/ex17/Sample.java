package jpl.ch02.ex17;

public class Sample {

	public static void main(String[] args) {
		Vehicle vehicle = new Vehicle("Yoshida");
		vehicle.setSpeed(60);
		vehicle.setDirection(30);
		
		System.out.println(vehicle.toString());
		
		vehicle.turn(30);
		System.out.println("Turned 30.");
		System.out.println(vehicle.toString());

		vehicle.turn(Vehicle.TURN_LEFT);
		System.out.println("Turned left.");
		System.out.println(vehicle.toString());

		vehicle.turn(Vehicle.TURN_RIGHT);
		System.out.println("Turned right.");
		System.out.println(vehicle.toString());

		vehicle.turn("TURN_LEFT_OR_RIGHT");
	}

}
