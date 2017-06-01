package gui.ex22;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.ex22.ClockValues.DecorativeFrame;

public class ClockView {
    private boolean isInitialized = false;

    private ClockController controller;
    private JFrame frame;
    private ClockPanel clockPanel;
    private JMenuBar menuBar;
    private PreferenceDialog preferenceDialog;

    public void init(ClockValues values, ClockController controller) {
        if (isInitialized) {
            throw new IllegalStateException("Use this method only once.");
        }
        if (values == null || controller == null) {
            throw new IllegalArgumentException("Values or controller is null.");
        }
        this.controller = controller;
        initFrame(values);
        initMenuBar();
        frame.setJMenuBar(menuBar);
        initClockPane(values);
        frame.getContentPane().add(clockPanel);
        initPreferenceDialog();
        frame.pack();
        isInitialized = true;
    }

    public void show() {
        if (!isInitialized) {
            throw new IllegalStateException("Invoke init first.");
        }
        frame.setVisible(true);    
    }

    private void initFrame(ClockValues values) {
        LocalDateTime time = LocalDateTime.now();
        frame = new JFrame(time.format(DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy")));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(ClockValues.DEFAULT_FRAME_SIZE);
        frame.setMinimumSize(ClockValues.MIN_FRAME_SIZE);
        frame.setBackground(values.bgColor());
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();

        JMenu menuView = new JMenu("View");
        menuBar.add(menuView);
        JMenuItem menuPreference = new JMenuItem("Preferences");
        menuView.add(menuPreference);
        menuPreference.addActionListener(controller.getActionListnerForMenuPreference());
    }

    private void initClockPane(ClockValues values) {
        clockPanel = new ClockPanel(frame, values);
        clockPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }


    private void initPreferenceDialog() {
        preferenceDialog = new PreferenceDialog(frame);
    }

    public void showPreferenceDialog(ClockValues values) {
        preferenceDialog.appear(values);
    }

    @SuppressWarnings("serial")
    class PreferenceDialog extends JDialog {

        private final Dimension LIST_DIMENSION = new Dimension(200, 75);

        private GridBagConstraints constraints;
        private ClockValues permValues;
        private ClockValues tempValues;
        private boolean actionListnersOn = false;

        private JList<ClockTheme> themeList;
        private JList<Font> fontList;
        private JRadioButton windowBaseBtn;
        private JRadioButton fontBaseBtn;
        private JList<Integer> fontSizeList;
        private JButton bgColorChooserBtn;
        private JButton fgColorChooserBtn;
        private JRadioButton[] decoRadioBtns;
        private Button applyBtn;
        private Button cancelBtn;
        
        public PreferenceDialog(JFrame parent) {
            super(parent);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    onCancelBtnPressed();
                }
            });
            this.setTitle("Preferences");
            this.setPreferredSize(ClockValues.FIXED_DIALOG_SIZE);
            this.setResizable(false);
            
            createAndAddComponents();
            this.pack();
        }

        public void appear(ClockValues values) {
            permValues = values;
            tempValues = values.snapShot();
            setSelectedItem(values);
            actionListnersOn = true;
            setVisible(true);
        }

        public void disappear() {
            permValues = null;
            tempValues = null;
            actionListnersOn = false;
            setVisible(false);
        }

        private void setSelectedItem(ClockValues values) {
            setSelectedItemOnList(themeList, values.themeIndex());
            setSelectedItemOnList(fontList, values.fontIndex());
            if (values.windowSizeDeterminesFontSize()) {
                windowBaseBtn.setSelected(true);
                fontSizeList.setEnabled(false);
            } else {
                fontBaseBtn.setSelected(false);
                fontSizeList.setEnabled(true);
                
                setSelectedItemOnList(fontSizeList, values.fontSizeIndex());
            }
            bgColorChooserBtn.setBackground(values.bgColor());
            fgColorChooserBtn.setBackground(values.fgColor());
            decoRadioBtns[values.decoration().index].setSelected(true);
        }

        private void setSelectedItemOnList(JList<?> list, int selectedIndex){
            if (selectedIndex >= 0) {
                list.setSelectedIndex(selectedIndex);
            } else {
                list.clearSelection();
            }
        };

        private void onThemeChanged(int index) {
            if (index < 0 || !actionListnersOn) {
                return;
            }
            
            tempValues.set(index);
            actionListnersOn =  false;
            setSelectedItemOnList(fontList, tempValues.fontIndex());
            bgColorChooserBtn.setBackground(tempValues.bgColor());
            fgColorChooserBtn.setBackground(tempValues.fgColor());
            decoRadioBtns[tempValues.decoration().index].setSelected(true);
            actionListnersOn =  true;
            changeClockPanelView(tempValues);
        }

        private void onFontChanged(int index) {
            if (index < 0 || !actionListnersOn) {
                return;
            }
            clearThemeSelection();
            tempValues.setFont(index);
            changeClockPanelView(tempValues);
        }

        private void onFontSizeChanged(int index) {
            if (index < 0 || !actionListnersOn) {
                return;
            }
            tempValues.setFontSize(index);
            changeClockPanelView(tempValues);
        }

        private void onBgColorChanged(Color color) {
            if (!actionListnersOn) {
                return;
            }
            clearThemeSelection();
            bgColorChooserBtn.setBackground(color);
            tempValues.setBgColor(color);
            changeClockPanelView(tempValues);
        }

        private void onFgColorChanged(Color color) {
            if (!actionListnersOn) {
                return;
            }
            clearThemeSelection();
            fgColorChooserBtn.setBackground(color);
            tempValues.setFgColor(color);
            changeClockPanelView(tempValues);
        }

        private void onWindowBasedBtnPressed() {
            actionListnersOn =  false;
            tempValues.setWindowSizeDeterminesFontSize(true);
            fontSizeList.clearSelection();
            actionListnersOn =  true;
            fontSizeList.setEnabled(false);
            changeClockPanelView(tempValues);
        }

        private void onFontBasedBtnPressed() {
            fontSizeList.setEnabled(true);
            actionListnersOn =  false;
            tempValues.setWindowSizeDeterminesFontSize(false);
            fontSizeList.setSelectedIndex(tempValues.fontSizeIndex());
            actionListnersOn =  true;
            changeClockPanelView(tempValues);
        }

        private void onDecorationBtnPressed(DecorativeFrame decoration) {
            clearThemeSelection();
            tempValues.setDecoration(decoration);
            changeClockPanelView(tempValues);
        }

        private void onApplyBtnPressed() {
            controller.onValuesChanged(tempValues);
            disappear();
        }

        private void onCancelBtnPressed() {
            clockPanel.setValues(permValues);
            disappear();
        }

        private void clearThemeSelection() {
            tempValues.setClockTheme(-1);
            setSelectedItemOnList(themeList, tempValues.themeIndex());
        }

        private void changeClockPanelView(ClockValues newValues) {
            clockPanel.setValues(newValues);
        }


        // Methods below create and add components.

        private void createAndAddComponents() {
            int rowIndex = 0;
            setLayout(new GridBagLayout());
            
            constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;

            createAndAddThemeList(20, rowIndex++);
            createAndAddFontList(10, rowIndex++);
            createAndAddFontSizeList(10, rowIndex++);
            createAndAddBgBtn(10, rowIndex++);
            createAndAddFgBtn(5, rowIndex++);
            createAndAddDecorationOptions(10, rowIndex++);
            createAndAddBottomBtns(5, rowIndex++);
        }

        private void createAndAddThemeList(int marginTop, int gridy) {
            themeList = createList(ClockValues.THEME_OPTIONS);
            JScrollPane themeListScroller = createScrollPane(themeList);
            addLabelAndInputCompoRow("Theme:", themeListScroller, marginTop, gridy);
            themeList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    onThemeChanged(themeList.getSelectedIndex());
                }
            });
        }

        private void createAndAddFontList(int marginTop, int gridy) {
            fontList = createList(ClockValues.FONT_OPTIONS);
            JScrollPane fontListScroller = createScrollPane(fontList);
            addLabelAndInputCompoRow("Font:", fontListScroller, marginTop, gridy);
            fontList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    onFontChanged(fontList.getSelectedIndex());
                }
            });
        }

        private void createAndAddFontSizeList(int marginTop, int gridy) {
            windowBaseBtn = new JRadioButton("Calculate from window size");
            fontBaseBtn = new JRadioButton("Select");
            JRadioButton[] btns = {windowBaseBtn, fontBaseBtn};
            JPanel panel = addAndPutRadioBtnsTogether(BoxLayout.Y_AXIS, btns);
            windowBaseBtn.setAlignmentX(LEFT_ALIGNMENT);
            windowBaseBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onWindowBasedBtnPressed();
                }
            });
            fontBaseBtn.setAlignmentX(LEFT_ALIGNMENT);
            fontBaseBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onFontBasedBtnPressed();
                }
            });
        
            fontSizeList = createList(ClockValues.FONT_SIZE_OPTIONS);
            JScrollPane fontSizeListScroller = createScrollPane(fontSizeList);
            panel.add(fontSizeListScroller);
            fontSizeListScroller.setAlignmentX(LEFT_ALIGNMENT);
            addLabelAndInputCompoRow("Font size:", panel, marginTop, gridy);
            fontSizeList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    onFontSizeChanged(fontSizeList.getSelectedIndex());
                }
            });
        }

        private void createAndAddBgBtn(int marginTop, int gridy) {
            bgColorChooserBtn = new JButton("");
            addLabelAndInputCompoRow("Color 1:", bgColorChooserBtn, marginTop, gridy);
            bgColorChooserBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color selectedBgColor = JColorChooser.showDialog(null, "Choose color", bgColorChooserBtn.getBackground());
                    if (selectedBgColor != null) {
                          onBgColorChanged(selectedBgColor);
                    }
                }
            });
        }

        private void createAndAddFgBtn(int marginTop, int gridy) {
            fgColorChooserBtn = new JButton("");
            addLabelAndInputCompoRow("Color 2:", fgColorChooserBtn, marginTop, gridy);
            fgColorChooserBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color selectedFgColor = JColorChooser.showDialog(null, "Choose color", fgColorChooserBtn.getBackground());
                    if (selectedFgColor != null) {
                          onFgColorChanged(selectedFgColor);
                    }
                }
            });
        }

        private void createAndAddDecorationOptions(int marginTop, int gridy) {
            decoRadioBtns = new JRadioButton[DecorativeFrame.values().length];
            for (DecorativeFrame deco: DecorativeFrame.values()) {
                decoRadioBtns[deco.index] = new JRadioButton(deco.name);
                decoRadioBtns[deco.index].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onDecorationBtnPressed(deco);
                    }
                });
            }
            JPanel panel = addAndPutRadioBtnsTogether(BoxLayout.X_AXIS, decoRadioBtns);

            addLabelAndInputCompoRow("Frame:", panel, marginTop, gridy);
        }

        private void createAndAddBottomBtns(int marginTop, int gridy) {
            Panel buttonPanel = new Panel();
            applyBtn = new Button("Apply");
            buttonPanel.add(applyBtn);
            applyBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onApplyBtnPressed();
                 }
              });
            
            cancelBtn = new Button("Cancel");
            buttonPanel.add(cancelBtn);
            cancelBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onCancelBtnPressed();
                 }
              });
            addLabelAndInputCompoRow("", buttonPanel, marginTop, gridy);
        }

        private <T> JList<T> createList(T[] data){
            JList<T> list = new JList<T>(data);
            list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            list.setLayoutOrientation(JList.VERTICAL);
            list.setVisibleRowCount(3);
            return list;
        };

        private JScrollPane createScrollPane(JList<?> list) {
            JScrollPane listScroller = new JScrollPane(list);
            listScroller.setPreferredSize(LIST_DIMENSION);
            listScroller.setMinimumSize(LIST_DIMENSION);
            listScroller.setMaximumSize(LIST_DIMENSION);
            return listScroller;
        }

        private JPanel addAndPutRadioBtnsTogether(int axis, JRadioButton[] btns) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, axis));
            ButtonGroup radios = new ButtonGroup();
            
            for (JRadioButton btn: btns) {
                radios.add(btn);
                panel.add(btn);
            }
            return panel;
        }

        private void addLabelAndInputCompoRow(String strLabel, Component compo, int marginTop, int gridy){
            addLabel(strLabel, marginTop, gridy);
            if (compo != null) {
                addInputCompo(compo, marginTop, gridy);
            } else {
                addLabel("", marginTop, gridy);
            }
        }

        private void addLabel(String strLabel, int marginTop, int gridy){
            Label label = new Label(strLabel);
            label.setFont(ClockValues.SYSTEM_FONT);
            constraints.weightx = 1.0;
            constraints.fill = GridBagConstraints.NONE;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.anchor = GridBagConstraints.EAST;
            constraints.insets = new Insets(marginTop,0,0,0);
            add(label, constraints);
        }
        
        private void addInputCompo(Component compo, int marginTop, int gridy){
            compo.setFont(ClockValues.SYSTEM_FONT);
            constraints.fill = GridBagConstraints.NONE;
            constraints.weightx = 1.0;
            constraints.gridx = 1;
            constraints.gridy = gridy;
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(marginTop,0,0,0);
            add(compo, constraints);   
        }
    }
}
