package jpl.ch02.ex14;

import jpl.ch02.ex13.Vehicle;

public class LinkedList {

	// ++++++ fields ++++++
	private Object object;
	private LinkedList nextItem;
	

	// ++++++ constructors ++++++
	public LinkedList(Object object) {
		this.object = object;
	}
	
	public LinkedList(Object object, LinkedList nextItem) {
		this(object);
		this.nextItem = nextItem;
	}
		
	
	// ++++++ accessor methods ++++++
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public LinkedList getNextItem() {
		return nextItem;
	}

	public void setNextItem(LinkedList linkedList) {
		this.nextItem = linkedList;
	}
	
	// ++++++ other methods ++++++
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
