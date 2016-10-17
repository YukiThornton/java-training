package jpl.ch01.ex03;

public class Fibonacci {

	public static void main(String[] args) {
		System.out.println("50以下のFibonnaci数列");
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
