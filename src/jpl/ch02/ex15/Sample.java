package jpl.ch02.ex15;

public class Sample {

	public static void main(String[] args) {
		Vehicle vehicle = new Vehicle("Kikuchi");
		vehicle.setDirection(43);
		vehicle.setSpeed(80);
		
		System.out.println(vehicle.toString());
		
		vehicle.changeSpeed(90);
		System.out.println("changeSpeed!");
		System.out.println(vehicle.toString());
		
		vehicle.stop();
		System.out.println("stop!");
		System.out.println(vehicle.toString());
	}

}
