package gui.ex12;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

public class Constants {
	public final static Dimension DEFAULT_FRAME_SIZE = new Dimension(400, 600);
	public final static Dimension MINIMUN_FRAME_SIZE = new Dimension(275, 160);

	public final static Dimension FIXED_DIALOG_SIZE = new Dimension(250, 440);
	
	public final static Font DEFAULT_FONT = new Font("arial black", Font.PLAIN, 24);
	
	public final static Color DEFAULT_BACKGROUND_COLOR = new Color(230, 225, 225);
	public final static Color DEFAULT_TEXT_COLOR_MAIN = new Color(77, 49, 49);
	public final static Color DEFAULT_TEXT_COLOR_FOOTER = new Color(112, 107, 107);
	public final static Color DEFAULT_TEXT_COLOR_SIDE = new Color(192, 108, 12);
}
