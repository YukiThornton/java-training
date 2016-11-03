package jpl.ch02.ex08;

import jpl.ch02.ex07.Vehicle;

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

		LinkedList linkedListA = new LinkedList(vehicleA);
		LinkedList linkedListB = new LinkedList(vehicleB);
		LinkedList linkedListC = new LinkedList(vehicleC);

		linkedListA.nextItem = linkedListB;
		linkedListB.nextItem = linkedListC;
		
		System.out.println("The 1st item: Vehicle No." + ((Vehicle)linkedListA.object).ID 
				+ ", the next one is No." + ((Vehicle)linkedListA.nextItem.object).ID);
		System.out.println("The 2nd item: Vehicle No." + ((Vehicle)linkedListB.object).ID 
				+ ", the next one is No." + ((Vehicle)linkedListB.nextItem.object).ID);
		System.out.println("The 3rd item: Vehicle No." + ((Vehicle)linkedListC.object).ID 
				+ ", the next one is " + linkedListC.nextItem);

	}
}
