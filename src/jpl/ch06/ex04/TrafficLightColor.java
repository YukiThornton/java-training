package jpl.ch06.ex04;

import java.awt.Color;

public enum TrafficLightColor {
	RED(Color.RED), YELLOW(Color.YELLOW), BLUE(Color.BLUE);
	
	private Color color;
	TrafficLightColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}
