package jpl.ch02.ex14;

public class Sample {

	public static void main(String[] args) {
		LinkedList linkedListA = new LinkedList("first object");
		
		System.out.println("設定前");
		System.out.println("Object: " + linkedListA.getObject());
		System.out.println("Next item: " + linkedListA.getNextItem());
		linkedListA.setObject("first object?");
		linkedListA.setNextItem(new LinkedList("second object"));
		System.out.println("設定後");
		System.out.println("Object: " + linkedListA.getObject());
		System.out.println("Next item: " + linkedListA.getNextItem());

	}

}
