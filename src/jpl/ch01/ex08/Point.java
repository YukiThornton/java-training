package jpl.ch01.ex08;

public class Point {
	public double x, y;
	
	public void moveTo (Point target) {
		this.x = target.x;
		this.y = target.y;
	}
}
