package gui.ex12;


import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

public class PreferenceDialog extends Dialog implements ItemListener, AdjustmentListener, ActionListener{
	
	private ClockFrame owner;
	private static List<Font> fontList;
	
	private List<Component> tabs;
	private List<Component> fontComponents;
	private List<Component> backgroundComponents;
	
	private Button fontButton;
	private Button backgroundButton;
	
	private Choice fontChoice;
	private Scrollbar fontSizeScrollbar;
	private Scrollbar[] mainTextScrollbars;
	private Scrollbar[] sideTextScrollbars;
	private Scrollbar[] footerTextScrollbars;
	
	private Scrollbar[] backgroundScrollbars;
	
	private static int selectedFontIndex = 0;
	private static int selectedFontSize = Constants.DEFAULT_FONT.getSize();
	private static Color selectedBackgroundColor = Constants.DEFAULT_BACKGROUND_COLOR;
	private static Color selectedMainTextColor = Constants.DEFAULT_TEXT_COLOR_MAIN;
	private static Color selectedSideTextColor = Constants.DEFAULT_TEXT_COLOR_SIDE;
	private static Color selectedFooterTextColor = Constants.DEFAULT_TEXT_COLOR_FOOTER;
	
	private final Font PREFERENCE_FONT = new Font("Dialog", Font.PLAIN, 15);
	
	static {
		fontList = new ArrayList<Font>();
		fontList.add(Constants.DEFAULT_FONT);
		for (Font font: GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
			fontList.add(font);
		}
	}
	
	public PreferenceDialog(ClockFrame owner) {
		super(owner);
		this.owner = owner;
		setup();
	}
	
	protected void setup() {
		setSize(Constants.FIXED_DIALOG_SIZE);
		setMinimumSize(Constants.FIXED_DIALOG_SIZE);
		setMaximumSize(Constants.FIXED_DIALOG_SIZE);
		setBackground(Constants.DEFAULT_BACKGROUND_COLOR);
		addWindowListener(new DialogWindowAdaptor());
		setLayout(null);
		
		/*
		TextField fontSizeInput = new TextField("aaa");
		fontSizeInput.setBounds(10, 10, 20, 10);
		add(fontSizeInput);
		*/
		
		tabs = new ArrayList<>();
		
		fontButton = new Button("Font");
		fontButton.addActionListener(this);
		fontButton.setBounds(20, 40, 60, 20);
		fontButton.setBackground(Constants.DEFAULT_BACKGROUND_COLOR);
		add(fontButton);
		
		tabs.add(fontButton);
		
		backgroundButton = new Button("Background");
		backgroundButton.addActionListener(this);
		backgroundButton.setBounds(80, 40, 100, 20);
		add(backgroundButton);
		
		tabs.add(backgroundButton);
		
		fontComponents = new ArrayList<>();
		
		Label labelFont = new Label("Font:");
		labelFont.setFont(PREFERENCE_FONT);
		labelFont.setBounds(20, 80, 80, 20);
		add(labelFont);
		fontComponents.add(labelFont);
		
		fontChoice = new Choice();
		fontChoice.setFont(PREFERENCE_FONT);
		fontChoice.add(fontList.get(0).getFontName() + " (Default)");
		for (int i = 1; i < fontList.size(); i++) {
			fontChoice.add(fontList.get(i).getFontName());
		}
		fontChoice.addItemListener(this);
		fontChoice.select(selectedFontIndex);
		fontChoice.setBounds(20, 100, 200, 20);
		add(fontChoice);
		fontComponents.add(fontChoice);
		
		Label labelFontSize = new Label("Font size:");
		labelFontSize.setFont(PREFERENCE_FONT);
		labelFontSize.setBounds(20, 120, 80, 20);
		add(labelFontSize);
		fontComponents.add(labelFontSize);
		
		fontSizeScrollbar = new Scrollbar(Scrollbar.HORIZONTAL, selectedFontSize, 10, 5, 150);
		fontSizeScrollbar.setBounds(20, 140, 200, 13);
		fontSizeScrollbar.addAdjustmentListener(this);
		add(fontSizeScrollbar);
		fontComponents.add(fontSizeScrollbar);
		
		Label labelMainTextFont = new Label("Font color (Main):");
		labelMainTextFont.setFont(PREFERENCE_FONT);
		labelMainTextFont.setBounds(20, 160, 200, 20);
		add(labelMainTextFont);
		fontComponents.add(labelMainTextFont);		
		mainTextScrollbars = makeContainerWithScrollbars(20, 180, selectedMainTextColor, fontComponents);
		
		Label labelSideTextFont = new Label("Font color (Sub):");
		labelSideTextFont.setFont(PREFERENCE_FONT);
		labelSideTextFont.setBounds(20, 240, 200, 20);
		add(labelSideTextFont);
		fontComponents.add(labelSideTextFont);		
		sideTextScrollbars = makeContainerWithScrollbars(20, 260, selectedSideTextColor, fontComponents);
		
		Label labelFooterTextFont = new Label("Font color (Footer):");
		labelFooterTextFont.setFont(PREFERENCE_FONT);
		labelFooterTextFont.setBounds(20, 320, 200, 20);
		add(labelFooterTextFont);
		fontComponents.add(labelFooterTextFont);		
		footerTextScrollbars = makeContainerWithScrollbars(20, 340, selectedFooterTextColor, fontComponents);
		
		backgroundComponents = new ArrayList<>();
		Label labelBackgroundColor = new Label("Background color:");
		labelBackgroundColor.setFont(PREFERENCE_FONT);
		labelBackgroundColor.setBounds(20, 80, 200, 20);
		add(labelBackgroundColor);
		backgroundComponents.add(labelBackgroundColor);		
		backgroundScrollbars = makeContainerWithScrollbars(20, 100, selectedBackgroundColor, backgroundComponents);
		turnOnBackgroundTab(false);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Scrollbar[] makeContainerWithScrollbars(int x, int y, Color selectedColor, List<Component> componentList) {
		Label labelR = new Label("R:");
		labelR.setFont(PREFERENCE_FONT);
		labelR.setBounds(x, y, 20, 13);
		add(labelR);
		componentList.add(labelR);
		Scrollbar red = new Scrollbar(Scrollbar.HORIZONTAL,selectedColor.getRed() , 10, 0, 265);
		red.setBounds(x + 20, y, 180, 13);
		red.addAdjustmentListener(this);
		add(red);
		componentList.add(red);
		
		Label labelG = new Label("G:");
		labelG.setFont(PREFERENCE_FONT);
		labelG.setBounds(x, y + 20, 20, 13);
		add(labelG);
		componentList.add(labelG);
		Scrollbar green = new Scrollbar(Scrollbar.HORIZONTAL, selectedColor.getGreen(), 10, 0, 265);
		green.setBounds(x + 20, y + 20, 180, 13);
		green.addAdjustmentListener(this);
		add(green);
		componentList.add(green);
		
		Label labelB = new Label("B:");
		labelB.setFont(PREFERENCE_FONT);
		labelB.setBounds(x, y + 40, 20, 13);
		add(labelB);
		componentList.add(labelB);
		Scrollbar blue = new Scrollbar(Scrollbar.HORIZONTAL, selectedColor.getBlue(), 10, 0, 265);
		blue.setBounds(x + 20, y + 40, 180, 13);
		blue.addAdjustmentListener(this);
		add(blue);
		componentList.add(blue);
		Scrollbar[] scrollbars = {red, green, blue};
		return scrollbars;
		
	}
	
	private void addRGBLabel() {
		
	}
	class DialogWindowAdaptor extends WindowAdapter {
		public void windowOpened(WindowEvent event) {
			System.out.println("Opened a dialog.");		
		}
		public void windowClosing(WindowEvent event) {
			System.out.println("Closing the dialog.");
			dispose();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getItemSelectable().equals(fontChoice)) {
			owner.changeFont(fontList.get(fontChoice.getSelectedIndex()));
			selectedFontIndex = fontChoice.getSelectedIndex();
			
		}
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (e.getSource().equals(backgroundScrollbars[0]) || e.getSource().equals(backgroundScrollbars[1]) || e.getSource().equals(backgroundScrollbars[2])) {
			selectedBackgroundColor = new Color(backgroundScrollbars[0].getValue(), backgroundScrollbars[1].getValue(), backgroundScrollbars[2].getValue());
			owner.changeBackGroundColor(selectedBackgroundColor);			
		} else if (e.getSource().equals(mainTextScrollbars[0]) || e.getSource().equals(mainTextScrollbars[1]) || e.getSource().equals(mainTextScrollbars[2])) {
			selectedMainTextColor = new Color(mainTextScrollbars[0].getValue(), mainTextScrollbars[1].getValue(), mainTextScrollbars[2].getValue());
			owner.changeTextMainColor(selectedMainTextColor);
		} else if (e.getSource().equals(sideTextScrollbars[0]) || e.getSource().equals(sideTextScrollbars[1]) || e.getSource().equals(sideTextScrollbars[2])) {
			selectedSideTextColor = new Color(sideTextScrollbars[0].getValue(), sideTextScrollbars[1].getValue(), sideTextScrollbars[2].getValue());
			owner.changeTextSideColor(selectedSideTextColor);			
		} else if (e.getSource().equals(footerTextScrollbars[0]) || e.getSource().equals(footerTextScrollbars[1]) || e.getSource().equals(footerTextScrollbars[2])) {
			selectedFooterTextColor = new Color(footerTextScrollbars[0].getValue(), footerTextScrollbars[1].getValue(), footerTextScrollbars[2].getValue());
			owner.changeTextFooterColor(selectedFooterTextColor);			
		} else if (e.getSource().equals(fontSizeScrollbar)) {
			selectedFontSize = e.getValue();
			owner.changeFontSize(e.getValue());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(backgroundButton)) {
			turnOnBackgroundTab(true);
		} else if (e.getSource().equals(fontButton)) {
			turnOnBackgroundTab(false);
		}
		
	}
	
	private void turnOnBackgroundTab (boolean isBackgroundTabOn) {
		for (Component component: backgroundComponents) {
			component.setVisible(isBackgroundTabOn);
		}
		for(Component component: fontComponents) {
			component.setVisible(!isBackgroundTabOn);
		}
		fontButton.setEnabled(isBackgroundTabOn);	
		backgroundButton.setEnabled(!isBackgroundTabOn);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
