package jpl.ch01.ex06;

public class Fibonacci {

	static final String TITLE = "50以下のFibonnaci数列";
	
	public static void main(String[] args) {
		System.out.println(TITLE);
		int lo = 1,
		hi = 1;
		System.out.println(lo);
		while (hi < 50) {
			System.out.println(hi);
			hi = lo + hi;
			lo = hi - lo;
		}
	}

}
