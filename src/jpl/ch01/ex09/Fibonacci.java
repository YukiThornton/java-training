package jpl.ch01.ex09;

public class Fibonacci {

	static final String TITLE = "50以下のFibonnaci数列";
	static final int MAX_NUM = 50;
	
	public static void main(String[] args) {
		System.out.println(TITLE);
		int[] sequence = new int[MAX_NUM];
		int lo = 1,
		hi = 1;
		sequence[0] = lo;
		for (int i = 1; hi < MAX_NUM; i++) {
			sequence[i] = hi;
			hi = lo + hi;
			lo = hi - lo;
		}
		for (int i = 0; i < MAX_NUM; i++) {
			if (sequence[i] == 0) {
				break;
			}
			System.out.println(sequence[i]);
		}
	}

}
