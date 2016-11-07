package jpl.ch01.ex12;

public class ImprovedFibonacci {
	static final int MAX_INDEX = 9;

	/**
	 * 偶数要素に'*'を付けて、フィボナッチ数列の
	 * 偶数の方の要素を表示する
	 */
	public static void main(String[] args) {
		int lo = 1;
		int hi = 1;
		String[] messages = new String[MAX_INDEX];

		messages[0] = "1: " + lo; 
		for (int i = 2; i <= MAX_INDEX; i++) {
			String message = i + ": " + hi;
			if (hi % 2 == 0) 
				message += " *";
			else
				message += "";
			messages[i-1] = message;
			hi = lo + hi;
			lo = hi - lo;
		}
		
		for (int i = 0; i < messages.length; i++) {
			System.out.println(messages[i]);
		}
	}

}
