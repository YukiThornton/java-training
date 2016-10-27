package jpl.ch02.ex16;

public class Sample {

	public static void main(String[] args) {
		LinkedList linkedListA = new LinkedList("abc");
		LinkedList linkedListB = new LinkedList("def");
		LinkedList linkedListC = new LinkedList("ghi");
		
		linkedListA.setNextItem(linkedListB);
		linkedListB.setNextItem(linkedListC);
		
		System.out.println(linkedListA.toString());
		System.out.println(linkedListA.length());
		System.out.println(linkedListB.toString());
		System.out.println(linkedListB.length());
		System.out.println(linkedListC.toString());
		System.out.println(linkedListC.length());
	}

}
