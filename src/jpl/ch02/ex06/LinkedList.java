package jpl.ch02.ex06;

import jpl.ch02.ex05.Vehicle;

public class LinkedList {
	public Object object;
	public LinkedList nextItem;
	
	public static void main(String[] args) {
		Vehicle vehicleA = new Vehicle();
		Vehicle vehicleB = new Vehicle();
		Vehicle vehicleC = new Vehicle();

		LinkedList linkedListA = new LinkedList();
		linkedListA.object = vehicleA;
		
		LinkedList linkedListB = new LinkedList();
		linkedListB.object = vehicleB;
		
		LinkedList linkedListC = new LinkedList();
		linkedListC.object = vehicleC;

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
