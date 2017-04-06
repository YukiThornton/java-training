package jpl.ch19.ex02;

import jpl.ch02.ex13.Vehicle;

/**
 * A <code>LinkedList</code> object defines a singly linked list node.
 * A node has an arbitrary <code>Object</code> and a reference to the next node.
 * 
 * @version 1.0
 * @since 1.0
 * @author Yuki
 *
 */
public class LinkedList {

	/**
	 * The object in this node.
	 */
	private Object object;
	
	/**
	 * The reference to the next node.
	 */
	private LinkedList nextItem;
	

	/**
	 * Creates a new node with the given object.
	 * The nextItem is initialized with <code>null</code>.
	 * @see LinkedList#LinkedList(Object, LinkedList)
	 */
	public LinkedList(Object object) {
		this.object = object;
	}
	
    /**
     * Creates a new node with the given object and the next node.
     * @see LinkedList#LinkedList(Object)
     */
	public LinkedList(Object object, LinkedList nextItem) {
		this(object);
		this.nextItem = nextItem;
	}
		
	
	/**
	 * Returns this node's object.
	 */
	public Object getObject() {
		return object;
	}
	
    /**
     * Sets the object of this node.
     */
	public void setObject(Object object) {
		this.object = object;
	}
	
    /**
     * Returns this node's next node.
     */
	public LinkedList getNextItem() {
		return nextItem;
	}

    /**
     * Sets this node's next node.
     */
	public void setNextItem(LinkedList linkedList) {
		this.nextItem = linkedList;
	}
	
	/**
     * Creates some nodes and prints out toString() result of the first node.
     * 
     * @param args An string array which is never used in this method.
     * @see #toString()
	 */
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
	
	/**
	 * Returns a string representation of this node and subsequent nodes.
	 */
	@Override
	public String toString() {
		String desc = object.toString();
		if (nextItem != null) {
			desc += "; " + nextItem.toString();
		}
		return desc;
	}
	
	/**
	 * Returns the length of this node's singly linked list.
	 */
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
}
