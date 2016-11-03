package jpl.ch02.ex11;

import jpl.ch02.ex10.Vehicle;;

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
		
		System.out.println(linkedListA);
	}
	
	public String toString() {
		String desc = object.toString();
		if (nextItem != null) {
			desc += "; " + nextItem.toString();
		}
		return desc;
	}
}
