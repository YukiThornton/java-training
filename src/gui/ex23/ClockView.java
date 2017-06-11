package gui.ex23;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JWindow;

import gui.ex23.ClockController.ClockMode;
import gui.ex23.ClockValues.DecorativeFrame;

public class ClockView {
    private boolean isInitialized = false;

    private ClockController controller;
    private ClockValues values;
    private int pointerX = 0;
    private int pointerY = 0;
    private JWindow window;
    private ClockPanel clockPanel;
    private ClockMode currentMode;

    private JRadioButtonMenuItem[] themeMenuItems;
    private ButtonGroup themeRadioMenuGroup;
    private JRadioButtonMenuItem[] fontMenuItems;
    private JRadioButtonMenuItem[] fontSizeMenuItems;
    private JMenuItem bgColMenuItem;
    private JMenuItem fgColMenuItem;
    private JCheckBoxMenuItem decorationMenuItem;
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
        this.values = values;
        this.controller = controller;
        initWindow();
        initClockPane();
        initMenuBar();
        window.getContentPane().add(clockPanel);
        window.pack();
        isInitialized = true;
    }

    public void show() {
        if (!isInitialized) {
            throw new IllegalStateException("Invoke init first.");
        }
        window.setVisible(true);
    }

    public void updateClockPanelView() {
        clockPanel.setValues(values);
    }

    public void deselectThemeMenuItem() {
        themeRadioMenuGroup.clearSelection();
    }

    public void selectMenuItemsExceptTheme() {
        fontMenuItems[values.fontIndex()].setSelected(true);
        fontSizeMenuItems[values.fontSizeIndex()].setSelected(true);
        bgColMenuItem.setIcon(new ColorSquareIcon(values.bgColor()));
        fgColMenuItem.setIcon(new ColorSquareIcon(values.fgColor()));
        decorationMenuItem.setSelected(values.decoration().equals(DecorativeFrame.OVAL));
    }

    public void changeBgColMenuItemSquare() {
        bgColMenuItem.setIcon(new ColorSquareIcon(values.bgColor()));
    }

    public void changeFgColMenuItemSquare() {
        fgColMenuItem.setIcon(new ColorSquareIcon(values.fgColor()));
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

    private void initWindow() {
        window = new JWindow();
        window.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point windowLocation = window.getLocation();
                window.setLocation(windowLocation.x + (e.getX() - pointerX), windowLocation.y + (e.getY() - pointerY));
            }
        });
        window.getContentPane().setPreferredSize(ClockValues.DEFAULT_FRAME_SIZE);
        window.setMinimumSize(ClockValues.MIN_FRAME_SIZE);
        window.setBackground(values.bgColor());
    }

    private void initClockPane() {
        clockPanel = new ClockPanel(window, values);
        clockPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void initMenuBar() {
        JPopupMenu popup = new JPopupMenu();
        popup.add(createPreMenu());
        popup.add(createModeMenu());
        popup.add(createTaskMenu());
        selectInitialModeAndTaskMenuItems();
        JMenuItem exitMenuItem = new JMenuItem(ClockValues.POPUP_COMMAND_EXIT);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.quit();
            }
        });
        popup.add(exitMenuItem);
        MouseListener popupListener = new PopupListener(popup);
        window.addMouseListener(popupListener);
    }

    private JMenu createPreMenu() {
        JMenu prefMenuItem = new JMenu(ClockValues.POPUP_COMMAND_PREF);
        prefMenuItem.add(createThemeMenuSet());
        prefMenuItem.add(createFontMenuSet());
        prefMenuItem.add(createFontSizeMenuSet());
        prefMenuItem.add(createBgColMenu());
        prefMenuItem.add(createFgColMenu());
        prefMenuItem.add(createDecorationMenu());
        selectInitialPrefMenuItems();
        return prefMenuItem;
    }

    private JMenu createModeMenu() {
        JMenu modeMenu = new JMenu(ClockValues.POPUP_COMMAND_MODE);
        ButtonGroup buttonGroup = new ButtonGroup();
        modeMenuItems = new JRadioButtonMenuItem[2];
        JRadioButtonMenuItem standardModeMenuItem = new JRadioButtonMenuItem(ClockValues.POPUP_COMMAND_STANDARD_MODE);
        modeMenuItems[ClockMode.CLOCK.index()] = standardModeMenuItem;
        JRadioButtonMenuItem taskModeMenuItem = new JRadioButtonMenuItem(ClockValues.POPUP_COMMAND_TASK_MODE);
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

    private JMenu createTaskNamedMenu(String tempName) {
        JMenu taskNamedMenu = new JMenu(tempName);
        // When you change the order of the code below, check out updateTaskNamedMenu too.
        JMenuItem taskStartMenu = new JMenuItem(ClockValues.POPUP_COMMAND_TASK_START);
        taskNamedMenu.add(taskStartMenu);
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
        JMenuItem taskResetMenu = taskNamedMenu.getItem(1);
        removeActionListeners(taskResetMenu);
        taskResetMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onTaskResetSelected(task);
            }
        });
    }

    private void removeActionListeners(JMenuItem item) {
        ActionListener[] listeners = item.getActionListeners();
        if (listeners.length != 0) {
            for (ActionListener listener: listeners) {
                item.removeActionListener(listener);
            }
        }
    }

    private void selectInitialPrefMenuItems() {
        if (isInitialized) {
            throw new IllegalStateException("Do not use this method after initialization.");
        }
        themeMenuItems[values.themeIndex()].setSelected(true);
        selectMenuItemsExceptTheme();
    }

    private JMenu createThemeMenuSet() {
        JMenu themeMenu = new JMenu(ClockValues.POPUP_COMMAND_THEME);
        themeRadioMenuGroup = new ButtonGroup();

        ClockTheme[] themes = ClockValues.THEME_OPTIONS;
        themeMenuItems = new JRadioButtonMenuItem[themes.length];
        for (int i = 0; i < themes.length; i++) {
            JRadioButtonMenuItem menuItem = createThemeMenuItem(themes[i].label());
            themeMenu.add(menuItem);
            themeRadioMenuGroup.add(menuItem);
            themeMenuItems[i] = menuItem;
        }
        return themeMenu;
    }

    private JRadioButtonMenuItem createThemeMenuItem(String label) {
        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(label);
        menuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                controller.onThemeSelected(findSelectedMenuIndex(themeMenuItems));
            }
        });
        return menuItem;
    }

    private JMenu createFontMenuSet() {
        JMenu fontMenu = new JMenu(ClockValues.POPUP_COMMAND_FONT);
        ButtonGroup group = new ButtonGroup();

        Font[] fonts = ClockValues.FONT_OPTIONS;
        fontMenuItems = new JRadioButtonMenuItem[fonts.length];
        for (int i = 0; i < fonts.length; i++) {
            JRadioButtonMenuItem menuItem = createFontMenuItem(fonts[i].toString());
            fontMenu.add(menuItem);
            group.add(menuItem);
            fontMenuItems[i] = menuItem;
        }
        return fontMenu;
    }

    private JRadioButtonMenuItem createFontMenuItem(String label) {
        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(label);
        menuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                controller.onFontSelected(findSelectedMenuIndex(fontMenuItems));
            }
        });
        return menuItem;
    }

    private JMenu createFontSizeMenuSet() {
        JMenu fontSizeMenu = new JMenu(ClockValues.POPUP_COMMAND_FONT_SIZE);
        ButtonGroup group = new ButtonGroup();

        Integer[] fontSizes = ClockValues.FONT_SIZE_OPTIONS;
        fontSizeMenuItems = new JRadioButtonMenuItem[fontSizes.length];
        for (int i = 0; i < fontSizes.length; i++) {
            JRadioButtonMenuItem menuItem = createFontSizeMenuItem(fontSizes[i].toString());
            fontSizeMenu.add(menuItem);
            group.add(menuItem);
            fontSizeMenuItems[i] = menuItem;
        }
        return fontSizeMenu;
    }

    private JRadioButtonMenuItem createFontSizeMenuItem(String label) {
        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(label);
        menuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                controller.onFontSizeSelected(findSelectedMenuIndex(fontSizeMenuItems));
            }
        });
        return menuItem;
    }

    private JMenuItem createBgColMenu() {
        bgColMenuItem = new JMenuItem(ClockValues.POPUP_COMMAND_COL1);
        bgColMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onBgColorSelected(JColorChooser.showDialog(null, "Choose color", values.bgColor()));
            }
        });
        return bgColMenuItem;
    }

    private JMenuItem createFgColMenu() {
        fgColMenuItem = new JMenuItem(ClockValues.POPUP_COMMAND_COL2);
        fgColMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onFgColorSelected(JColorChooser.showDialog(null, "Choose color", values.fgColor()));
            }
        });
        return fgColMenuItem;
    }

    private JCheckBoxMenuItem createDecorationMenu() {
        decorationMenuItem = new JCheckBoxMenuItem(ClockValues.POPUP_COMMAND_DECORATION);
        decorationMenuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                controller.onDecorationSelected(decorationMenuItem.getState());
            }
        });
        return decorationMenuItem;
    }

    private int findSelectedMenuIndex(JRadioButtonMenuItem[] menuItems) {
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }

    private static class ColorSquareIcon implements Icon {
    
        private int width = 10;
        private int height = 10;
        private Color color;

        public ColorSquareIcon(Color color) {
            this.color = color;
        }

        @Override
        public int getIconHeight() {
            return height;
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x +1 ,y + 1,width -2 ,height -2);
        }
    }

    class PopupListener extends MouseAdapter {
        JPopupMenu popup;
 
        PopupListener(JPopupMenu popupMenu) {
            popup = popupMenu;
        }
 
        public void mousePressed(MouseEvent e) {
            pointerX = e.getX();
            pointerY = e.getY();
            maybeShowPopup(e);
        }
 
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }
 
        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
