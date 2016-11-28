package jpl.ch04.ex02;

public interface ComparableObject {
	/**
	 * 第１引数と第２引数を比較して、その結果を整数値で返す.
	 * 第１引数が第２引数より大きい時は正の数
	 * 第１引数と第２引数が等しい時は0.
	 * 第１引数が第２引数より小さい時は負の数を返す。
	 * @param o1
	 * @param o2
	 * @return 比較結果の整数値
	 */
	abstract int doComapre(Object o1, Object o2);
}
