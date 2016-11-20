package jpl.ch05.ex01;

/*
 * Answer to 5.1
 * AttributedインターフェースはAttrクラスにネスとしたインターフェースであるべき。
 * AttributedインターフェースとAttrクラスはが同じ文脈で使われるため
 * どちらかがとちらかのネストした型であるべき。
 * Attrインスタンスを使いやすくするために、Attributedインターフェースをネストしたメンバとした
 */
public class Attr {
	
	// +++++ fileds +++++
	
	/**
	 * 属性の名前を保持するフィールド.
	 */
	private final String name;
	
	/**
	 * 属性オブジェクトを保持するフィールド.
	 */
	private Object value = null;
	

	// +++++ constructor +++++
	public Attr(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	
	// +++++ getters, setters +++++
	public String getName() {
		return name;
	}
	
	public Object getValue() {
		return value;
	}

	/**
	 * 新しいオブジェクトをvalueに格納する.
	 * @param newValue 
	 * @return valueに格納されていた古いオブジェクト
	 */
	public Object setValue(Object newValue) {
		Object oldVal = value;
		value = newValue;
		return oldVal;
	}
	
	/**
	 * Attrクラスのインスタンスを表現した文字列を返すメソッド.
	 * @return インスタンスを表現した文字列
	 */
	public String toString() {
		return name + "='" + value + "'";
	}

	public interface Attributed {
		void add(Attr newAttr);
		Attr find(String attrName);
		Attr remove(String attrName);
		java.util.Iterator<Attr> attrs();		
	}
}
