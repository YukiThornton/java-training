package jpl.ch04.ex03;

public class LinkedString implements LinkedList{

	// ++++++ fields ++++++
	private String string;
	private LinkedList nextItem;
	

	// ++++++ constructors ++++++
	public LinkedString(String string) {
		this.string = string;
	}
	
	public LinkedString(String string, LinkedList nextItem) {
		this(string);
		this.nextItem = nextItem;
	}
		
	
	// ++++++ getters and setters ++++++
	public Object getObject() {
		return string;
	}
	
	public void setObject(Object object) {
		this.string = (String)object;
	}
	
	public LinkedList getNextItem() {
		return nextItem;
	}

	public void setNextItem(LinkedList linkedList) {
		this.nextItem = linkedList;
	}
	
	// ++++++ other methods ++++++	
	public String toString() {
		String desc = string.toString();
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
		LinkedList clonedList = new LinkedString((String)getObject());
		clonedList.setNextItem(getNextItem());
		return clonedList;
	}
	
	public static void main(String[] args) {
		String string1 = "string1";
		String string2 = "string2";
		String string3 = "string3";

		LinkedList linkedListA = new LinkedString(string1);
		LinkedList linkedListB = new LinkedString(string2);
		LinkedList linkedListC = new LinkedString(string3);

		linkedListA.setNextItem(linkedListB);
		linkedListB.setNextItem(linkedListC);
		
		System.out.println(linkedListA);
	}
	
}
