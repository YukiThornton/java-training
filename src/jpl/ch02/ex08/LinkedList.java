package jpl.ch02.ex08;

import jpl.ch02.ex07.*;

public class LinkedList {
	public Object object;
	public LinkedList nextItem;
	
	public LinkedList(Object object) {
		this.object = object;
	}
	
	public LinkedList(Object object, LinkedList nextItem) {
		this(object);
		this.nextItem = nextItem;
	}
	
	public static void main(String[] args) {
		Vehicle vehicleA = new Vehicle();
		Vehicle vehicleB = new Vehicle();
		Vehicle vehicleC = new Vehicle();

		LinkedList linkedListC = new LinkedList(vehicleC);
		LinkedList linkedListB = new LinkedList(vehicleB, linkedListC);
		LinkedList linkedListA = new LinkedList(vehicleA, linkedListB);
	}
}
