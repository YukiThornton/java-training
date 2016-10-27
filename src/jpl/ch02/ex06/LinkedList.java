package jpl.ch02.ex06;

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
		linkedListA.nextItem = linkedListA;
		linkedListB.object = vehicleB;
		
		LinkedList linkedListC = new LinkedList();
		linkedListB.nextItem = linkedListB;
		linkedListC.object = vehicleC;
	}
}
