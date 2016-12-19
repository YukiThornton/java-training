package jpl.ch10.ex04;

/*
 * p.205 練習問題10.4
 * 今までの練習問題からforループを使用した問題を数台選んで、whileループで書き直す
 * do-whileでも書き直すことができるか？
 * そのように書き直したりするか？
 * しないとしたら、なぜか？
 * 
 * Answer:
 * 練習問題1.13 ImprovedFibonacciのmainメソッドではwhile文でもdo-while文でも書き直すことができる。
 * 出力させたい数列の長さが１より大きい場合はdo-while文では書き直せない。
 */
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

		System.out.printf("1: %2d%n", lo);
		/*
		for (int i = 2; i <= MAX_INDEX; i++) {
			if (hi % 2 == 0) 
				mark = " *";
			else
				mark = "";
			System.out.printf(i + ": %2d" + mark + "%n", hi);
			hi = lo + hi;
			lo = hi - lo;
		}
		*/
		
		/*
		int count = 2;
		while(count <= MAX_INDEX) {
			if (hi % 2 == 0) 
				mark = " *";
			else
				mark = "";
			System.out.printf(count + ": %2d" + mark + "%n", hi);
			hi = lo + hi;
			lo = hi - lo;
			count++;			
		}
		*/
		
		int count = 2;
		do {
			if (hi % 2 == 0) 
				mark = " *";
			else
				mark = "";
			System.out.printf(count + ": %2d" + mark + "%n", hi);
			hi = lo + hi;
			lo = hi - lo;
			count++;			
			
		} while (count <= MAX_INDEX);
	}

}
