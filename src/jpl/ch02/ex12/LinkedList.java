package jpl.ch02.ex12;

/*
 * Answer: 
 * 複数のLinkedListオブジェクトを引数に取ってそれらを評価するようなstaticメソッドであれば
 * 可変長引数メソッドが必要である。
 * 例として、以下にlinkedFromメソッドを実装した。
 */

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

		LinkedList linkedListC = new LinkedList(vehicleC);
		LinkedList linkedListB = new LinkedList(vehicleB, linkedListC);
		LinkedList linkedListA = new LinkedList(vehicleA, linkedListB);
		
		System.out.println(linkedListA);
		System.out.println(linkedListB);
		System.out.println(linkedListC);
		
		System.out.println(linkedListB.object + " is linked from " + linkedFrom(linkedListB, linkedListA, linkedListB, linkedListC).object);
	}
	
	public String toString() {
		String desc = object.toString();
		if (nextItem != null) {
			desc += "; " + nextItem.toString();
		}
		return desc;
	}
}
