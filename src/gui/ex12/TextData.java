package gui.ex12;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

class TextData {
	private String text;
	private Font font;
	private Color textColor;
	private Graphics graphics;
	private Dimension dimension;
	private Point point;
	
	TextData(String text, Font font, Color color, Graphics graphics) {
		this.text = text;
		this.font = font;
		this.textColor = color;
		this.graphics = graphics;
		setDimension();
	}
	
	private void setDimension() {			
		FontMetrics fontMetrics = graphics.getFontMetrics(font);
		dimension = new Dimension(fontMetrics.stringWidth(text), fontMetrics.getHeight());
	}
	
	void locate(Point point) {
		this.point = point;
	}
	
	void locate(double x, double y) {
		point = new Point((int)x, (int)y);
	}
	
	void locate(int x, int y) {
		point = new Point(x, y);
	}
	
	void draw() {
		if (point == null) {
			throw new IllegalStateException();
		}
		graphics.setColor(textColor);
		graphics.setFont(font);
		graphics.drawString(text, point.x, point.y);
	}
	
	double width() {
		return dimension.getWidth();
	}
	
	double height() {
		return dimension.getHeight();
	}
	
	double x() {
		return point.getX();
	}
	
	double y() {
		return point.getY();
	}
}
