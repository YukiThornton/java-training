package jpl.ch03.ex10;

import jpl.ch02.ex13.Vehicle;

public class LinkedList implements Cloneable {

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
	public String toString() {
		String desc = object.toString();
		if (nextItem != null) {
			desc += "; " + nextItem.toString();
		}
		return desc;
	}
	
	public int length() {
		int count = 1;
		LinkedList item = getNextItem();
		while(true) {
			if (item != null) {
				count++;
				item = item.getNextItem();
			} else {
				break;
			}
		}
		return count;
	}
	
	/**
	 * 元のインスタンスが保持するオブジェクトへの参照をコピーするメソッド.
	 */
	public LinkedList clone() {
		LinkedList clonedList = new LinkedList(getObject());
		clonedList.setNextItem(getNextItem());
		return clonedList;
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
	
}
