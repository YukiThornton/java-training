package gui.ex24;

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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.ex24.ClockController.ClockMode;
import gui.ex24.ClockValues.DecorativeFrame;

public class ClockView {
    private boolean isInitialized = false;

    private ClockController controller;
    private JFrame frame;
    private ClockPanel clockPanel;
    private JMenuBar menuBar;
    private PreferenceDialog preferenceDialog;
    private ClockMode currentMode;
    private JRadioButtonMenuItem[] modeMenuItems;
    private JMenu taskMenu;
    private JMenu[] taskNamedMenus;
    private JMenuItem taskPauseMenu;

    public void init(ClockMode mode, ClockValues values, ClockController controller) {
        if (isInitialized) {
            throw new IllegalStateException("Use this method only once.");
        }
        if (values == null || controller == null) {
            throw new IllegalArgumentException("Values or controller is null.");
        }
        currentMode = mode;
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

    public void setEnableTaskPauseMenu(boolean enabled) {
        taskPauseMenu.setEnabled(enabled);
    }

    public void setEnableTaskNamedMenus(boolean enabled) {
        for (JMenu menu: taskNamedMenus) {
            menu.setEnabled(enabled);
        }
    }

    public void showTask(ClockTask task) {
        clockPanel.setTask(task);
    }

    public void stopShowingTask() {
        clockPanel.setTask(null);
    }

    public void showTaskMode() {
        clockPanel.setModeRelatedMessage(ClockValues.MODE_MESSAGE_TASK);
    }

    public void stopShowingTaskMode() {
        clockPanel.setModeRelatedMessage(null);
    }

    private void initFrame(ClockValues values) {
        LocalDateTime time = LocalDateTime.now();
        frame = new JFrame(time.format(DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy")));
        WindowListener exitListner = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.onWindowClosing();
            }
        };
        frame.addWindowListener(exitListner);
        Rectangle rectangle = values.initialRectangle();
        frame.getContentPane().setPreferredSize(rectangle.getSize());
        frame.setMinimumSize(ClockValues.MIN_FRAME_SIZE);
        frame.setLocation(rectangle.getLocation());
        frame.setBackground(values.bgColor());
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        JMenu modeMenu = createModeMenu();
        menuBar.add(modeMenu);
        JMenu menuTask = createTaskMenu();
        menuBar.add(menuTask);
        JMenu menuView = createViewMenu();
        menuBar.add(menuView);
        
        JMenuItem exitMenuItem = new JMenuItem(ClockValues.POPUP_COMMAND_EXIT);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onWindowClosing();
            }
        });
        selectInitialModeAndTaskMenuItems();
    }

    private JMenu createViewMenu() {
        JMenu viewMenuItem = new JMenu(ClockValues.POPUP_COMMAND_VIEW);
        JMenuItem menuPreference = new JMenuItem(ClockValues.POPUP_COMMAND_PREF);
        menuPreference.addActionListener(controller.getActionListnerForMenuPreference());
        viewMenuItem.add(menuPreference);
        return viewMenuItem;
    }

    private JMenu createModeMenu() {
        JMenu modeMenu = new JMenu(ClockValues.POPUP_COMMAND_MODE);
        ButtonGroup buttonGroup = new ButtonGroup();
        modeMenuItems = new JRadioButtonMenuItem[2];
        JRadioButtonMenuItem standardModeMenuItem = new JRadioButtonMenuItem(ClockValues.POPUP_COMMAND_MODE_STANDARD);
        modeMenuItems[ClockMode.CLOCK.index()] = standardModeMenuItem;
        JRadioButtonMenuItem taskModeMenuItem = new JRadioButtonMenuItem(ClockValues.POPUP_COMMAND_MODE_TASK);
        modeMenuItems[ClockMode.TASK.index()] = taskModeMenuItem;
        for (JRadioButtonMenuItem item: modeMenuItems) {
            modeMenu.add(item);
            buttonGroup.add(item);
            item.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    controller.onModeSelected(findSelectedMenuIndex(modeMenuItems));
                }
            });
        }
        return modeMenu;
    }

    private JMenu createTaskMenu() {
        taskMenu = new JMenu(ClockValues.POPUP_COMMAND_TASK);
        taskPauseMenu = new JMenuItem(ClockValues.POPUP_COMMAND_TASK_PAUSE);
        taskPauseMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onTaskPauseSelected();
            }
        });
        taskMenu.add(taskPauseMenu);
        taskNamedMenus = new JMenu[ClockValues.TASK_AMOUNT];
        for (int i = 0; i < taskNamedMenus.length; i++) {
            JMenu menu = createTaskNamedMenu("");
            taskMenu.add(menu);
            taskNamedMenus[i] = menu;
        }
        return taskMenu;
    }

    private void initClockPane(ClockValues values) {
        clockPanel = new ClockPanel(frame, values);
        clockPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }


    private void initPreferenceDialog() {
        preferenceDialog = new PreferenceDialog(frame);
    }

    private void selectInitialModeAndTaskMenuItems() {
        modeMenuItems[currentMode.index()].setSelected(true);
        switch(currentMode) {
        case CLOCK:
            setupForStandardMode();
            break;
        case TASK:
            new IllegalStateException("Something went wrong!");
            break;
        default:
            break;
        }
    }

    public void setupForStandardMode() {
        currentMode = ClockMode.CLOCK;
        taskMenu.setEnabled(false);
    }

    public void setupForTaskMode(ClockTask[] tasks) {
        currentMode = ClockMode.TASK;
        taskPauseMenu.setEnabled(false);
        updateTaskNamedMenus(tasks);
        taskMenu.setEnabled(true);
    }

    public String showTaskRenameDialog(String message, String oldTaskName, String initialVal) {
        return (String) JOptionPane.showInputDialog(frame, message, "Rename " + oldTaskName, JOptionPane.PLAIN_MESSAGE, null, null, initialVal);
    }

    private JMenu createTaskNamedMenu(String tempName) {
        JMenu taskNamedMenu = new JMenu(tempName);
        // When you change the order of the code below, check out updateTaskNamedMenu too.
        JMenuItem taskStartMenu = new JMenuItem(ClockValues.POPUP_COMMAND_TASK_START);
        taskNamedMenu.add(taskStartMenu);
        JMenuItem taskRenameMenu = new JMenuItem(ClockValues.POPUP_COMMAND_TASK_RENAME);
        taskNamedMenu.add(taskRenameMenu);
        JMenuItem taskResetMenu = new JMenuItem(ClockValues.POPUP_COMMAND_TASK_RESET);
        taskNamedMenu.add(taskResetMenu);
        return taskNamedMenu;
    }

    private void updateTaskNamedMenus(ClockTask[] tasks) {
        if (taskNamedMenus.length != tasks.length) {
            throw new IllegalArgumentException("Something went wrong!");
        }
        for (int i = 0; i < taskNamedMenus.length; i++) {
            updateTaskNamedMenu(taskNamedMenus[i], tasks[i]);
        }
    }

    private void updateTaskNamedMenu(JMenu taskNamedMenu, ClockTask task) {
        taskNamedMenu.setText(task.name());
        JMenuItem taskStartMenu = taskNamedMenu.getItem(0);
        removeActionListeners(taskStartMenu);
        taskStartMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onTaskStartSelected(task);
            }
        });
        JMenuItem taskRenameMenu = taskNamedMenu.getItem(1);
        removeActionListeners(taskRenameMenu);
        taskRenameMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onTaskRenameSelected(task);
            }
        });
        JMenuItem taskResetMenu = taskNamedMenu.getItem(2);
        removeActionListeners(taskResetMenu);
        taskResetMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onTaskResetSelected(task);
            }
        });
    }

    public void updateNamesOfTaskNamedMenus(ClockTask[] tasks) {
        if (taskNamedMenus.length != tasks.length) {
            throw new IllegalArgumentException("Something went wrong!");
        }
        for (int i = 0; i < tasks.length; i++) {
            taskNamedMenus[i].setText(tasks[i].name());
        }
    }

    private void removeActionListeners(JMenuItem item) {
        ActionListener[] listeners = item.getActionListeners();
        if (listeners.length != 0) {
            for (ActionListener listener: listeners) {
                item.removeActionListener(listener);
            }
        }
    }

    public void showPreferenceDialog(ClockValues values) {
        preferenceDialog.appear(values);
    }

    public Rectangle getFrameBounds() {
        return frame.getBounds();
    }

    private int findSelectedMenuIndex(JRadioButtonMenuItem[] menuItems) {
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i].isSelected()) {
                return i;
            }
        }
        return -1;
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
                fontBaseBtn.setSelected(true);
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
