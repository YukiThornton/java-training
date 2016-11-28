package jpl.ch03.ex07;

/**
 * 色属性クラス.
 * @author okuno
 *
 */
public class ColorAttr extends Attr {
	
	// +++++ fileds +++++
	
	/**
	 * ScreenColorオブジェクトを保持するフィールド
	 */
	private ScreenColor myColor;

	
	// +++++ constructor +++++
	
	public ColorAttr(String name, Object value) {
		super(name, value);
		decodeColor();
	}
	
	public ColorAttr(String name) {
		this(name, "transparent");
	}
	
	public ColorAttr(String name, ScreenColor value) {
		super(name, value.toString());
		myColor = value;
	}
	
	
	// +++++ getters, setters +++++
	
	public Object setValue(Object newValue) {
		Object retval = super.setValue(newValue);
		decodeColor();
		return retval;
	}
	
	public ScreenColor setValue(ScreenColor newValue) {
		super.setValue(newValue.toString());
		ScreenColor oldValue = myColor;
		myColor = newValue;
		return oldValue;
	}
	
	public ScreenColor getColor() {
		return myColor;
	}
	
	// +++++ other methods +++++
	
	protected void decodeColor() {
		if (getValue() == null)
			myColor = null;
		else
			myColor = new ScreenColor(getValue());
		
	}
	
	/**
	 * インスタンスと引数のオブジェクトを比較して、以下の場合にfalseを返すメソッド.
	 * 1. オブジェクトがColorAttrのインスタンスでない場合
	 * 2. インスタンスと引数のオブジェクトのnameが異なる文字列の場合
	 * 3. インスタンスと引数のオブジェクトのvalueをtoStringした結果が異なる文字列の場合
	 * @param object 比較対象のオブジェクト
	 * @return 上記1~3のいずれかを満たす場合にfalse
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ColorAttr)) {
			return false;
		}
		ColorAttr ca = (ColorAttr)object;
		if (!this.getName().equals(ca.getName())) {
			return false;
		}
		if (!this.getValue().toString().equals(ca.getValue().toString())) {
			return false;
		}
		return true;
	}
	
	/**
	 * name, valueからハッシュ値を作成して返すメソッド
	 * @return ハッシュ値
	 */
	public int hashCode() {
		int h = 31;
		h = h * 31 + (getName() == null ? 0: getName().hashCode());
		h = h * 31 + (getValue() == null ? 0: getValue().toString().hashCode());
		return h;
	}
	
}
