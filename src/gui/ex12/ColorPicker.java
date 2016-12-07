package gui.ex12;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class ColorPicker extends Canvas {
	Color[] colors;
	
	public ColorPicker() {
		Color[] color = {Color.BLACK, Color.RED, Color.BLUE, Color.DARK_GRAY};
		colors = color;
	}

	public void paint(Graphics graphics) {
		int x = 40;
		for (Color color: colors) {
			graphics.setColor(color);
			graphics.fillRect(x, 200, 40, 40);
			x += 40;
		}

	}
}
