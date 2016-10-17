package jpl.ch01.ex08;

public class Sample {

	public static void main(String[] args) {
		Point a = new Point();
		a.x = 1.0;
		a.y = 2.0;
		
		Point b = new Point();
		b.x = 3.0;
		b.y = 4.0;
		
		System.out.println("a(" + a.x + ", " + a.y + ")");
		System.out.println("b(" + b.x + ", " + b.y + ")");
		
		b.moveTo(a);
		System.out.println("Point b moved to Point a.");
		
		System.out.println("a(" + a.x + ", " + a.y + ")");
		System.out.println("b(" + b.x + ", " + b.y + ")");
	}

}
