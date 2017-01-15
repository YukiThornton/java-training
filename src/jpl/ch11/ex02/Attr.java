package jpl.ch11.ex02;

/**
 * 基本的な属性クラス.
 * @author okuno
 *
 */
public class Attr<V> {
	
	// +++++ fileds +++++
	
	/**
	 * 属性の名前を保持するフィールド.
	 */
	private final String name;
	
	/**
	 * 属性オブジェクトを保持するフィールド.
	 */
	private V value = null;
	

	// +++++ constructor +++++
	public Attr(String name, V value) {
		this.name = name;
		this.value = value;
	}

	
	// +++++ getters, setters +++++
	public String getName() {
		return name;
	}
	
	public V getValue() {
		return value;
	}

	/**
	 * 新しいオブジェクトをvalueに格納する.
	 * @param newValue 
	 * @return valueに格納されていた古いオブジェクト
	 */
	public V setValue(V newValue) {
		V oldVal = value;
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
}
