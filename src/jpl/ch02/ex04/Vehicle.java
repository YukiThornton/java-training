package jpl.ch02.ex04;

public class Vehicle {
	private static long nextId = 1; // Vehicleオブジェクトが生成されるたびに変更される値のため、finalにすべきではない
	
	public final long ID = nextId++; // オブジェクトが生成される時に決まり、そのあと変更されることがないため、finalにすべき
	public double speed;
	public double direction;
	public String owner;
	
}
