package gui.ex11;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Clock {
	
	public static void main(String[] args) {
		start();
	}
	
	protected static void start() {
		MyFrame frame = new MyFrame();
		frame.setVisible(true);		
	}

	@SuppressWarnings("serial")
	protected static class MyFrame extends Frame {
		private final int DEFAULT_FRAME_X = 400;
		private final int DEFAULT_FRAME_Y = 600;
		private final String DEFAULT_FONT_FAMILY = "arial black";
		private final int DEFAULT_FONT_SIZE = 24;
		
		public MyFrame() {
			super("Clock");
			setSize(DEFAULT_FRAME_X, DEFAULT_FRAME_Y);
			setBackground(new Color(230, 225, 225));
			addWindowListener(new MyWindowAdaptor());
		}
		
		public void paint(Graphics g) {
			LocalDateTime now = LocalDateTime.now();
			String hourString = now.format(DateTimeFormatter.ofPattern("hh:mm"));
			String secondString = now.format(DateTimeFormatter.ofPattern("ss"));
			String amPmString = now.format(DateTimeFormatter.ofPattern("a", Locale.ENGLISH));
			String dateString = now.format(DateTimeFormatter.ofPattern("EEEE - MMMM d", Locale.ENGLISH));
			
			Font mainFont = new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE * 2);
			Font sideFont = new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE);
			Font footerFont = new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE);

			FontMetrics mainFontMetrics = g.getFontMetrics(mainFont);
			int mainWidth = mainFontMetrics.stringWidth(hourString);
			int mainHeight = mainFontMetrics.getHeight();
			int mainWidthIncludingSingleSpace = mainFontMetrics.stringWidth(hourString + " ");
			FontMetrics sideFontMetrics = g.getFontMetrics(sideFont);
			int sideFontHeight = sideFontMetrics.getHeight();
			FontMetrics footerFontMetrics = g.getFontMetrics(footerFont);
			int footerWidth = footerFontMetrics.stringWidth(dateString);
			
			Point basePoint = new Point(DEFAULT_FRAME_X / 2, DEFAULT_FRAME_Y / 2);
			Point mainCorner = new Point(basePoint.x - (mainWidthIncludingSingleSpace / 2), basePoint.y - (mainHeight / 2));
			Point sideConrerForSecond = new Point(mainCorner.x + mainWidth, mainCorner.y);
			Point sideConrerForAmPm = new Point(sideConrerForSecond.x, mainCorner.y - (sideFontHeight /2) - 5);
			Point footerCorner = new Point(basePoint.x - (footerWidth / 2), mainCorner.y + 50);
			
			Color baseTextColor = new Color(77, 49, 49);
			Color lighterTextColor = new Color(112, 107, 107);
			Color burntOrange = new Color(192, 108, 12);
			
			// main [hh:mm]
			g.setColor(baseTextColor);
			g.setFont(mainFont);
			g.drawString(hourString, mainCorner.x, mainCorner.y);

			// side [ss]
			g.setColor(baseTextColor);
			g.setFont(sideFont);
			g.drawString(secondString, sideConrerForSecond.x, sideConrerForSecond.y);
			
			// side[a]
			g.setColor(burntOrange);
			g.setFont(sideFont);
			g.drawString(amPmString, sideConrerForAmPm.x, sideConrerForAmPm.y);
			
			// footer[EEEE - MMMM d]
			g.setColor(lighterTextColor);
			g.setFont(footerFont);
			g.drawString(dateString, footerCorner.x, footerCorner.y);

			repaint();
		}
		
	}

	protected static class MyWindowAdaptor extends WindowAdapter {
		public void windowOpened(WindowEvent event) {
			System.out.println("Opened a window.");		
		}
		public void windowClosing(WindowEvent event) {
			System.out.println("Closing the window.");
			System.exit(0);
		}
	}
	
}
