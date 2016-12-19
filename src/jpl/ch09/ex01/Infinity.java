package jpl.ch09.ex01;

/*
 * p.177 練習問題 9.1
 * 2つの無限大オペランドに対して演算子+, -, *, /を使用するプログラムを作成して結果を表示する
 * どちらも同じ符号の値と、異なる符号の値の両方を試す
 */
public class Infinity {

	public static void main(String[] args) {
		System.out.println("POSITIVE + POSITIVE: " + toText(Double.POSITIVE_INFINITY + Double.POSITIVE_INFINITY));
		System.out.println("POSITIVE - POSITIVE: " + toText(Double.POSITIVE_INFINITY - Double.POSITIVE_INFINITY));
		System.out.println("POSITIVE * POSITIVE: " + toText(Double.POSITIVE_INFINITY * Double.POSITIVE_INFINITY));
		System.out.println("POSITIVE / POSITIVE: " + toText(Double.POSITIVE_INFINITY / Double.POSITIVE_INFINITY));
		System.out.println("");
		System.out.println("NEGATIVE + NEGATIVE: " + toText(Double.NEGATIVE_INFINITY + Double.NEGATIVE_INFINITY));
		System.out.println("NEGATIVE - NEGATIVE: " + toText(Double.NEGATIVE_INFINITY - Double.NEGATIVE_INFINITY));
		System.out.println("NEGATIVE * NEGATIVE: " + toText(Double.NEGATIVE_INFINITY * Double.NEGATIVE_INFINITY));
		System.out.println("NEGATIVE / NEGATIVE: " + toText(Double.NEGATIVE_INFINITY / Double.NEGATIVE_INFINITY));
		System.out.println("");
		System.out.println("POSITIVE + NEGATIVE: " + toText(Double.POSITIVE_INFINITY + Double.NEGATIVE_INFINITY));
		System.out.println("POSITIVE - NEGATIVE: " + toText(Double.POSITIVE_INFINITY - Double.NEGATIVE_INFINITY));
		System.out.println("POSITIVE * NEGATIVE: " + toText(Double.POSITIVE_INFINITY * Double.NEGATIVE_INFINITY));
		System.out.println("POSITIVE / NEGATIVE: " + toText(Double.POSITIVE_INFINITY / Double.NEGATIVE_INFINITY));
		System.out.println("");
		System.out.println("NEGATIVE + POSITIVE: " + toText(Double.NEGATIVE_INFINITY + Double.POSITIVE_INFINITY));
		System.out.println("NEGATIVE - POSITIVE: " + toText(Double.NEGATIVE_INFINITY - Double.POSITIVE_INFINITY));
		System.out.println("NEGATIVE * POSITIVE: " + toText(Double.NEGATIVE_INFINITY * Double.POSITIVE_INFINITY));
		System.out.println("NEGATIVE / POSITIVE: " + toText(Double.NEGATIVE_INFINITY / Double.POSITIVE_INFINITY));
	}
	
	private static String toText(double value) {
		if (Double.isInfinite(value)) {
			if (value > 0) {
				return "+∞";
			} else {
				return "-∞";				
			}
		}
		return Double.toString(value);
	}

}
