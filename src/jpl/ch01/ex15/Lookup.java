package jpl.ch01.ex15;

/**
 * nameと関連付けられた値を返す。
 * その様な値がなければnullを返す。
 */
public interface Lookup {
	Object find(String name);
}
