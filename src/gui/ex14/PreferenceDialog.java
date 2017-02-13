package gui.ex14;


import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class PreferenceDialog extends Dialog {
	
	private ClockFrame owner;
	private ClockPreference clockPreference;
	private GridBagConstraints constraints;
	
    private Choice themeChoice;
	private Choice fontChoice;
	private Choice fontSizeChoice;
    private Choice bgColorChoice;
    private Choice mainTxtColorChoice;
    private Choice footerTxtColorChoice;
    private Choice sideTxtColorChoice;
    Button applyBtn;
    Button cancelBtn;
	
	private final Font PREFERENCE_FONT = new Font("Dialog", Font.PLAIN, 15);
    private final static Dimension FIXED_DIALOG_SIZE = new Dimension(400, 500);
	
	
	public PreferenceDialog(ClockFrame owner, ClockPreference preference) {
		super(owner);
		this.owner = owner;
		this.clockPreference = preference;
		setup();
        setMinimumSize(FIXED_DIALOG_SIZE);
        setMaximumSize(FIXED_DIALOG_SIZE);
	}
	
    private void setup() {
        setSize(FIXED_DIALOG_SIZE);
        setBackground(Constants.DEFAULT_BACKGROUND_COLOR);
        addWindowListener(new DialogWindowAdaptor());
        addComponents();
    }
    
    private void addComponents() {
        int rowIndex = 0;
        setLayout(new GridBagLayout());
        
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        themeChoice = new Choice();
        themeChoice.setFont(PREFERENCE_FONT);
        for (int i = 0; i < ClockPreference.getThemeOptions().length; i++) {
            themeChoice.add(ClockPreference.getThemeOptions()[i].label());
        }
        themeChoice.select(clockPreference.getSelectedThemeIndex());
        themeChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                themeChanged(themeChoice.getSelectedIndex());
            }
        });
        addLabelAndChoiceRow("Theme:", themeChoice, 20, rowIndex++);
 
        fontChoice = new Choice();
        fontChoice.setFont(PREFERENCE_FONT);
        for (int i = 0; i < ClockPreference.getFontOptions().length; i++) {
            fontChoice.add(ClockPreference.getFontOptions()[i].getFontName());
        }
        fontChoice.select(clockPreference.getSelectedTxtFontIndex());
        addLabelAndChoiceRow("Font:", fontChoice, 30, rowIndex++);
 
        fontSizeChoice = new Choice();
        for (int i = 0; i < ClockPreference.getTxtSizeOptions().length; i++) {
            fontSizeChoice.add(Integer.toString(ClockPreference.getTxtSizeOptions()[i]));
        }
        fontSizeChoice.select(clockPreference.getSelectedTxtSizeIndex());
        addLabelAndChoiceRow("Text size:", fontSizeChoice, 20, rowIndex++);
        
        bgColorChoice = new Choice();
        setColorOptionsToChoice(bgColorChoice, clockPreference.getSelectedBgColorIndex());
        addLabelAndChoiceRow("Background color:", bgColorChoice, 20, rowIndex++);
        
        addLabelAndChoiceRow("Text color", null, 20, rowIndex++);
        
        mainTxtColorChoice = new Choice();
        setColorOptionsToChoice(mainTxtColorChoice, clockPreference.getSelectedMainTxtColorIndex());
        addLabelAndChoiceRow("Time:", mainTxtColorChoice, 10, rowIndex++);
        
        footerTxtColorChoice = new Choice();
        setColorOptionsToChoice(footerTxtColorChoice, clockPreference.getSelectedFooterTxtColorIndex());
        addLabelAndChoiceRow("Date:", footerTxtColorChoice, 10, rowIndex++);
        
        sideTxtColorChoice = new Choice();
        setColorOptionsToChoice(sideTxtColorChoice, clockPreference.getSelectedSideTxtColorIndex());
        addLabelAndChoiceRow("AM/PM:", sideTxtColorChoice, 10, rowIndex++);
        
        Panel buttonPanel = new Panel(new GridBagLayout());
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 0;       //reset to default
        constraints.weighty = 1.0;   //request any extra vertical space
        constraints.anchor = GridBagConstraints.PAGE_END; //bottom of space
        constraints.insets = new Insets(10,0,0,0);  //top padding
        constraints.gridx = 1;       //aligned with button 2
        constraints.gridy = rowIndex;       //third row

        GridBagConstraints btnConstraints = new GridBagConstraints();
        btnConstraints.fill = GridBagConstraints.HORIZONTAL;

        applyBtn = new Button("Apply");
        btnConstraints.fill = GridBagConstraints.NONE;
        btnConstraints.gridx = 0;       //aligned with button 2
        btnConstraints.gridwidth = 1;   //2 columns wide
        btnConstraints.gridy = 0;       //third row
        buttonPanel.add(applyBtn, btnConstraints);
        applyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyChange();
             }
          });
        
        cancelBtn = new Button("Cancel");
        btnConstraints.fill = GridBagConstraints.NONE;
        btnConstraints.gridx = 1;       //aligned with button 2
        btnConstraints.gridwidth = 1;   //2 columns wide
        btnConstraints.gridy = 0;       //third row
        buttonPanel.add(cancelBtn, btnConstraints);
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
             }
          });
        
       
        add(buttonPanel, constraints);
    }
    
    private void setColorOptionsToChoice(Choice choice, int selected){
        for (int i = 0; i < ClockPreference.getColorOptions().length; i++) {
            choice.add(ClockPreference.getColorOptions()[i].label());
        }
        choice.select(selected);
    }
    
	private void addLabelAndChoiceRow(String strLabel, Choice choice, int marginTop, int gridy){
	    addLabel(strLabel, marginTop, gridy);
	    if (choice != null) {
	        addChoice(choice, marginTop, gridy);
	    } else {
	        addLabel("", marginTop, gridy);
	    }
	}
	
    private void addLabel(String strLabel, int marginTop, int gridy){
        Label label = new Label(strLabel);
        label.setFont(PREFERENCE_FONT);
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(marginTop,0,0,0);
        add(label, constraints);
    }
    
    private void addChoice(Choice choice, int marginTop, int gridy){
        choice.setFont(PREFERENCE_FONT);
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = gridy;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(marginTop,0,0,0);
        add(choice, constraints);   
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
	
	private void themeChanged(int selectedIndex){
	    ClockTheme theme = ClockPreference.getThemeOptions()[selectedIndex];
	    
	    fontChoice.select(theme.txtFont().getFontName());
	    bgColorChoice.select(theme.bgColor().label());
	    mainTxtColorChoice.select(theme.mainTxtColor().label());
	    footerTxtColorChoice.select(theme.footerTxtColor().label());
	    sideTxtColorChoice.select(theme.sideTxtColor().label());
	}
	
	private void applyChange() {
	    clockPreference.setTheme(themeChoice.getSelectedIndex());
        clockPreference.setTxtFont(fontChoice.getSelectedIndex());
        clockPreference.setTxtSize(fontSizeChoice.getSelectedIndex());
        clockPreference.setBgColor(bgColorChoice.getSelectedIndex());
        clockPreference.setMainTxtColor(mainTxtColorChoice.getSelectedIndex());
        clockPreference.setFooterTxtColor(footerTxtColorChoice.getSelectedIndex());
        clockPreference.setSideTxtColor(sideTxtColorChoice.getSelectedIndex());
        owner.absorbPreferenceChange();
        dispose();        
    }
	
}
