package jpl.ch01.ex10;

public class ImprovedFibonacci {
	static final int MAX_INDEX = 9;

	/**
	 * 偶数要素に'*'を付けて、フィボナッチ数列の
	 * 偶数の方の要素を表示する
	 */
	public static void main(String[] args) {
		Number[] sequence = new Number[MAX_INDEX];
		
		Number lo = new Number();
		lo.num = 1;
		lo.isEven = false;
		sequence[0] = lo;
		
		Number hi = new Number();
		hi.num = 1;
		hi.isEven = false;		
		sequence[1] = hi;

		for (int i = 2; i < sequence.length; i++) {
			Number newNum = new Number();
			newNum.num = sequence[i-2].num + sequence[i-1].num;
			newNum.isEven = newNum.num % 2 == 0;
			sequence[i] = newNum;
		}
		
		for (int i = 0; i < sequence.length; i++) {
			String mark;
			if (sequence[i].isEven)
				mark = " *";
			else
				mark = "";
			System.out.println((i + 1) + ": " + sequence[i].num + mark);			
		}
	}

}
