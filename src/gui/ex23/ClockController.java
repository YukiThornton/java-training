package gui.ex23;

import java.awt.Color;

import gui.ex23.ClockValues.DecorativeFrame;

public class ClockController {
    private ClockView view;
    private ClockValues values;
    private boolean isInitialized = false;
    private boolean changingTheme = false;
    
    public ClockController(ClockView view, ClockValues values) {
        if (view == null) {
            throw new IllegalStateException("view is null.");
        }
        this.view = view;
        this.values = values;
        view.init(values, this);
        isInitialized = true;
    }
    
    public void start() {
        view.show();
    }
    
    public void onValuesChanged(ClockValues newValues) {
        values = newValues;
    }

    public void quit() {
        System.exit(0);
    }

    public void onThemeSelected(int index) {
        if (index < 0 || !isInitialized) {
            return;
        }
        changingTheme = true;
        values.set(index);
        view.selectMenuItemsExceptTheme();
        changingTheme = false;
        view.updateClockPanelView();
    }

    public void onFontSelected(int index) {
        if (!shouldChangeView(index)) {
            return;
        }
        clearThemeSelection();
        values.setFont(index);
        view.updateClockPanelView();
    }

    public void onFontSizeSelected(int index) {
        if (!shouldChangeView(index)) {
            return;
        }
        values.setFontSize(index);
        view.updateClockPanelView();
    }

    public void onBgColorSelected(Color color) {
        if (!shouldChangeView(color)) {
            return;
        }
        clearThemeSelection();
        values.setBgColor(color);
        view.updateClockPanelView();
        view.changeBgColMenuItemSquare();
    }

    public void onFgColorSelected(Color color) {
        if (!shouldChangeView(color)) {
            return;
        }
        clearThemeSelection();
        values.setFgColor(color);
        view.updateClockPanelView();
        view.changeFgColMenuItemSquare();
    }

    public void onDecorationSelected(boolean state) {
        if (!shouldChangeView()) {
            return;
        }
        clearThemeSelection();
        if (state) {
            values.setDecoration(DecorativeFrame.OVAL);
        } else {
            values.setDecoration(DecorativeFrame.NONE);
        }
        view.updateClockPanelView();
    }

    private void clearThemeSelection() {
        values.setClockTheme(-1);
        view.deselectThemeMenuItem();
    }

    private boolean shouldChangeView() {
        return isInitialized && !changingTheme;
    }

    private boolean shouldChangeView(int selectedIndex) {
        return selectedIndex >= 0 && shouldChangeView();
    }

    private boolean shouldChangeView(Color selectedColor) {
        if (selectedColor == null) {
            return false;
        }
        return shouldChangeView();
    }
}
