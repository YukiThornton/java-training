package gui.ex12;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@SuppressWarnings("serial")
class ClockCanvas extends Canvas implements Runnable {

	private String fontFamily = Constants.DEFAULT_FONT.getName();
	private int fontStyle = Constants.DEFAULT_FONT.getStyle();
	private int baseFontSize = Constants.DEFAULT_FONT.getSize();
	private final int PAINT_INTERVAL = 50;
	
	private Image imgBuffer;
	private Graphics graphicBuffer;
	private LocalDateTime now;
	private Color backgroundColor = Constants.DEFAULT_BACKGROUND_COLOR;
	private Color textColorMain = Constants.DEFAULT_TEXT_COLOR_MAIN;
	private Color textColorFooter = Constants.DEFAULT_TEXT_COLOR_FOOTER;
	private Color textColorSide = Constants.DEFAULT_TEXT_COLOR_SIDE;
	
	private TextData dateText;
	private TextData periodText;
	
	int getFontSize () {
		return baseFontSize;
	}
	
	void changeFont (Font font) {
		fontFamily = font.getFontName();
		fontStyle = font.getStyle();
		repaint();
	}
	
	void changeFontSize (int size) {
		baseFontSize = size;
		repaint();
	}
	
	void changeBackgroundColor (Color color) {
		backgroundColor = color;
		repaint();
	}
	
	void changeMainTextColor (Color color) {
		textColorMain = color;
		repaint();
	}
	
	void changeSideTextColor (Color color) {
		textColorSide = color;
		repaint();
	}
	
	void changeFooterTextColor (Color color) {
		textColorFooter = color;
		repaint();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(PAINT_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public void paint(Graphics graphics) {
		Dimension dimension = getSize();
		if (imgBuffer != null && imgBuffer.getWidth(this) == dimension.width && imgBuffer.getHeight(this) == dimension.height && now.getSecond() == LocalDateTime.now().getSecond()) {
			return;
		}
		
		imgBuffer = createImage(dimension.width, dimension.height);
		graphicBuffer = imgBuffer.getGraphics();
		
		graphicBuffer.setColor(backgroundColor);
		graphicBuffer.fillRect(0, 0, dimension.width, dimension.height);
		
		now = LocalDateTime.now();
		
		Font mainFont = new Font(fontFamily, fontStyle, baseFontSize * 2);
		Font sideFont = new Font(fontFamily, fontStyle, baseFontSize);
		Font footerFont = new Font(fontFamily, fontStyle, baseFontSize);

		Point basePoint = new Point(dimension.width / 2, dimension.height / 2);
		//TextData center = new TextData("a", sideFont, baseTextColor, graphicBuffer);
		//center.locate(basePoint);
		//center.draw();		
		
		// main [hh:mm]
		String hourString = now.format(DateTimeFormatter.ofPattern("hh:mm"));
		TextData hourText = new TextData(hourString, mainFont, textColorMain, graphicBuffer);
		TextData hourTextWithSpace = new TextData(hourString + " ", mainFont, textColorMain, graphicBuffer);
		hourText.locate(basePoint.x - (hourTextWithSpace.width() / 2), basePoint.y);
		hourText.draw();

		// side [ss]
		TextData secondText = new TextData(now.format(DateTimeFormatter.ofPattern("ss")), sideFont, textColorMain, graphicBuffer);
		secondText.locate(hourText.x() + hourText.width(), hourText.y());
		secondText.draw();
		
		// side[a]
		periodText = new TextData(now.format(DateTimeFormatter.ofPattern("a", Locale.ENGLISH)), sideFont, textColorSide, graphicBuffer);
		periodText.locate(secondText.x(), hourText.y() - secondText.height()/2 - (5 * baseFontSize / Constants.DEFAULT_FONT.getSize()));
		periodText.draw();
		
		// footer[EEEE - MMMM d]
		dateText = new TextData(now.format(DateTimeFormatter.ofPattern("EEEE - MMMM d", Locale.ENGLISH)), footerFont, textColorFooter, graphicBuffer);
		dateText.locate(basePoint.x - (dateText.width() / 2), hourText.y() + (int)(30 * baseFontSize / Constants.DEFAULT_FONT.getSize()));
		dateText.draw();
	
		graphics.drawImage(imgBuffer, 0, 0, this);		
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public double getDateTextWidth() {
		return dateText.width();
	}
	
	public double getTotalTextHeight() {
		return (dateText.y() - periodText.y()) + dateText.height();
	}
	
}
