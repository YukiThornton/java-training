package jpl.ch02.ex11;

import jpl.ch02.ex10.*;

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
		
		System.out.println(linkedListA);
		System.out.println(linkedListB);
		System.out.println(linkedListC);
	}
	
	public String toString() {
		String desc = object.toString();
		if (nextItem != null) {
			desc += "; " + nextItem.toString();
		}
		return desc;
	}
}
