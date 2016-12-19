package gui.ex12;


import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Scrollbar;
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

@SuppressWarnings("serial")
public class PreferenceDialog extends Dialog implements ItemListener, AdjustmentListener, ActionListener{
	
	private ClockFrame owner;
	private List<Font> fontList;
	
	private List<Component> tabs;
	private List<Component> fontComponents;
	private List<Component> backgroundComponents;
	
	private Button fontButton;
	private Button backgroundButton;
	
	private Choice fontChoice;
	private Scrollbar fontSizeScrollbar;
	private Checkbox radioButtonForMain;
	private Checkbox radioButtonForSub;
	private Checkbox radioButtonForFooter;	
	private Scrollbar[] textColorScrollbars;
	
	private Scrollbar[] backgroundScrollbars;
	
	private int selectedFontIndex = 0;
	private int selectedFontSize = Constants.DEFAULT_FONT.getSize();
	private Checkbox selectedRadioButtonForTextColor;
	private Color selectedBackgroundColor = Constants.DEFAULT_BACKGROUND_COLOR;
	private Color selectedMainTextColor = Constants.DEFAULT_TEXT_COLOR_MAIN;
	private Color selectedSideTextColor = Constants.DEFAULT_TEXT_COLOR_SIDE;
	private Color selectedFooterTextColor = Constants.DEFAULT_TEXT_COLOR_FOOTER;
	
	private final Font PREFERENCE_FONT = new Font("Dialog", Font.PLAIN, 15);
	
	public static PreferenceDialog getPreferenceDialog(ClockFrame owner) {
		PreferenceDialog preferenceDialog = new PreferenceDialog(owner);
		preferenceDialog.setup();
		return preferenceDialog;
		
	}
	private PreferenceDialog(ClockFrame owner) {
		super(owner);
		this.owner = owner;
	}
	
	private void setup() {
		setSize(Constants.FIXED_DIALOG_SIZE);
		setMinimumSize(Constants.FIXED_DIALOG_SIZE);
		setMaximumSize(Constants.FIXED_DIALOG_SIZE);
		setBackground(Constants.DEFAULT_BACKGROUND_COLOR);
		addWindowListener(new DialogWindowAdaptor());
		setLayout(null);
		
		fontList = new ArrayList<Font>();
		fontList.add(Constants.DEFAULT_FONT);
		for (Font font: GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
			fontList.add(font);
		}
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
		
		fontSizeScrollbar = new Scrollbar(Scrollbar.HORIZONTAL, selectedFontSize, 10, Constants.MINIMUM_FONT_SIZE, Constants.MAXIMUM_FONT_SIZE + 10);
		fontSizeScrollbar.setBounds(20, 140, 200, 13);
		fontSizeScrollbar.addAdjustmentListener(this);
		add(fontSizeScrollbar);
		fontComponents.add(fontSizeScrollbar);
		
		CheckboxGroup radioButtonsForTextColor = new CheckboxGroup();
		radioButtonForMain = new Checkbox("Main", true, radioButtonsForTextColor);
		radioButtonForMain.setBounds(20, 180, 70, 20);
		radioButtonForMain.addItemListener(this);
		add(radioButtonForMain);
		fontComponents.add(radioButtonForMain);		
		radioButtonForSub = new Checkbox("Sub", false, radioButtonsForTextColor);
		radioButtonForSub.setBounds(90, 180, 60, 20);
		radioButtonForSub.addItemListener(this);
		add(radioButtonForSub);
		fontComponents.add(radioButtonForSub);		
		radioButtonForFooter = new Checkbox("Footer", false, radioButtonsForTextColor);
		radioButtonForFooter.setBounds(150, 180, 80, 20);
		radioButtonForFooter.addItemListener(this);
		add(radioButtonForFooter);
		fontComponents.add(radioButtonForFooter);
		selectedRadioButtonForTextColor = radioButtonForMain;
		
		Label labelForTextFont = new Label("Font color:");
		labelForTextFont.setFont(PREFERENCE_FONT);
		labelForTextFont.setBounds(20, 160, 200, 20);
		add(labelForTextFont);
		fontComponents.add(labelForTextFont);		
		textColorScrollbars = makeContainerWithScrollbars(20, 200, selectedMainTextColor, fontComponents);
		
		backgroundComponents = new ArrayList<>();
		Label labelBackgroundColor = new Label("Background color:");
		labelBackgroundColor.setFont(PREFERENCE_FONT);
		labelBackgroundColor.setBounds(20, 80, 200, 20);
		add(labelBackgroundColor);
		backgroundComponents.add(labelBackgroundColor);		
		backgroundScrollbars = makeContainerWithScrollbars(20, 100, selectedBackgroundColor, backgroundComponents);
		turnOnBackgroundTab(false);
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
			owner.getCanvas().changeFont(fontList.get(fontChoice.getSelectedIndex()));
			selectedFontIndex = fontChoice.getSelectedIndex();
		} else if (e.getItemSelectable().equals(radioButtonForMain)) {
			changeTextColorScrollbarTarget(radioButtonForMain, selectedMainTextColor);
		} else if (e.getItemSelectable().equals(radioButtonForSub)) {
			changeTextColorScrollbarTarget(radioButtonForSub, selectedSideTextColor);
		} else if (e.getItemSelectable().equals(radioButtonForFooter)) {
			changeTextColorScrollbarTarget(radioButtonForFooter, selectedFooterTextColor);
		}
	}
	
	private void changeTextColorScrollbarTarget(Checkbox selected, Color previousColor) {
		selectedRadioButtonForTextColor = selected;
		textColorScrollbars[0].setValue(previousColor.getRed());
		textColorScrollbars[1].setValue(previousColor.getGreen());
		textColorScrollbars[2].setValue(previousColor.getBlue());
		System.out.println("previousColor" + previousColor.getRed() + ","  + previousColor.getGreen() + ","  + previousColor.getBlue());
	}

	private void changeTextColor(Color newColor) {
		if (selectedRadioButtonForTextColor == radioButtonForMain) {
			selectedMainTextColor = newColor;
			owner.getCanvas().changeMainTextColor(selectedMainTextColor);
		} else if (selectedRadioButtonForTextColor == radioButtonForSub) {
			selectedSideTextColor = newColor;
			owner.getCanvas().changeSideTextColor(selectedSideTextColor);			
		} else if (selectedRadioButtonForTextColor == radioButtonForFooter) {
			selectedFooterTextColor = newColor;
			owner.getCanvas().changeFooterTextColor(selectedFooterTextColor);			
		}
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (e.getSource().equals(backgroundScrollbars[0]) || e.getSource().equals(backgroundScrollbars[1]) || e.getSource().equals(backgroundScrollbars[2])) {
			selectedBackgroundColor = new Color(backgroundScrollbars[0].getValue(), backgroundScrollbars[1].getValue(), backgroundScrollbars[2].getValue());
			owner.getCanvas().changeBackgroundColor(selectedBackgroundColor);			
		} else if (e.getSource().equals(textColorScrollbars[0]) || e.getSource().equals(textColorScrollbars[1]) || e.getSource().equals(textColorScrollbars[2])) {
			Color newColor = new Color(textColorScrollbars[0].getValue(), textColorScrollbars[1].getValue(), textColorScrollbars[2].getValue());
			changeTextColor(newColor);
		}  else if (e.getSource().equals(fontSizeScrollbar)) {
			changeFontSize(e.getValue());
		}
	}
	public void changeFontSize(int newSize) {
		if (newSize < Constants.MINIMUM_FONT_SIZE) {
			newSize = Constants.MINIMUM_FONT_SIZE;
		} else if (newSize > Constants.MAXIMUM_FONT_SIZE) {
			newSize = Constants.MAXIMUM_FONT_SIZE;
		}
		selectedFontSize = newSize;
		fontSizeScrollbar.setValue(selectedFontSize);
		owner.getCanvas().changeFontSize(selectedFontSize);		
	}
	public void enlargeTextFont() {
		changeFontSize(selectedFontSize + 10);
	}

	public void shrinkTextFont() {
		changeFontSize(selectedFontSize - 10);
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
	}
}
