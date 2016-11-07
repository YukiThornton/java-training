package jpl.ch01.ex07;

public class ImprovedFibonacci {
	static final int MAX_INDEX = 9;

	/**
	 * 偶数要素に'*'を付けて、フィボナッチ数列の
	 * 偶数の方の要素を表示する
	 */
	public static void main(String[] args) {
		int lo = 1;
		int hi = 1;
		String mark;
		
		System.out.println("1: " + lo);
		int count = 1;
		for (int i = MAX_INDEX; i >= 2; i--) {
			count++;
			if (hi % 2 == 0) 
				mark = " *";
			else
				mark = "";
			System.out.println(count + ": " + hi + mark);
			hi = lo + hi;
			lo = hi - lo;
		}
	}

}
