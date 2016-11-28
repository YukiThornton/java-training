package jpl.ch03.ex07;

public class ScreenColor {
	
	
	// +++++ fields +++++
	
	private Object value;
	
	
	// +++++ constructors +++++
	
	public ScreenColor(Object value) {
		this.value = value;
	}
	
	
	// +++++ getters, setters +++++
	
	public Object getValue() {
		return value;
	}

	
	// +++++ other methods +++++
	
	public String toString() {
		return value.toString();
	}

}
