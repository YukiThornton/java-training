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
	/**
	 * 第１引数が第２引数以降に渡されたオブジェクトの中のどのオブジェクトからリンクされているかを示すメソッド.
	 * 第１引数にリンクするオブジェクトがあればそのオブジェクトが、
	 * なければnullが返る。
	 * @param target, linkedLists
	 * @return 第１引数にリンクするオブジェクトまたはnull
	 */
	public static LinkedList linkedFrom(LinkedList target, LinkedList... linkedLists) throws NullPointerException {
		if (linkedLists == null) {
			throw new NullPointerException("The argument should not be null.");
		}
		for (int i = 0; i < linkedLists.length; i++) {
			if (linkedLists[i].nextItem == target) {
				return linkedLists[i];
			}
		}
		return null;
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
