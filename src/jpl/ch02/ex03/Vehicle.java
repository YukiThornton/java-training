package jpl.ch02.ex03;

public class Vehicle {
	private static long nextId = 1;
	
	public long id = nextId++;
	public double speed;
	public double direction;
	public String owner;
}
