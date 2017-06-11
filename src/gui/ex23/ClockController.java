package gui.ex23;

import java.awt.Color;

import gui.ex23.ClockValues.DecorativeFrame;

public class ClockController {
    private ClockView view;
    private ClockValues values;
    private TaskController taskController;
    private boolean isInitialized = false;
    private boolean changingTheme = false;
    private ClockMode currentMode = ClockMode.CLOCK;
    private ClockTask[] tasks = null;
    
    enum ClockMode {
        CLOCK(0), TASK(1);
        int index;
        private ClockMode(int index) {
            this.index = index;
        }
        public int index() {
            return index;
        }
    }
    
    public ClockController(ClockView view, ClockValues values) {
        if (view == null) {
            throw new IllegalStateException("view is null.");
        }
        this.view = view;
        this.values = values;
        view.init(currentMode, values, this);
        taskController = new TaskController(values, view);
        isInitialized = true;
    }
    
    public void start() {
        view.show();
    }
    
    public void onValuesChanged(ClockValues newValues) {
        values = newValues;
    }

    public void quit() {
        if (currentMode == ClockMode.TASK) {
            quitTaskMode();
        }
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

    public void onModeSelected(int modeIndex) {
        if (!isInitialized || modeIndex < 0) {
            return;
        }
        for (ClockMode m : ClockMode.values()) {
            if (m.index() == modeIndex) {
                currentMode = m;
            }
        }
        switch(currentMode) {
        case CLOCK:
            setupForStandardMode();
            break;
        case TASK:
            setupForTaskMode();
            break;
        default:
            break;
        }
    }

    private void setupForStandardMode() {
        quitTaskMode();
        view.setupForStandardMode();
        tasks = null;
    }

    private void setupForTaskMode() {
        tasks = taskController.tasks();
        view.setupForTaskMode(tasks);
    }

    private void quitTaskMode() {
        if (taskController.isTaskRunning()) {
            pauseAndSaveTask();
        }
    }

    public void onTaskStartSelected(ClockTask task) {
        taskController.select(task);
        taskController.startTask();
        view.setEnableTaskPauseMenu(true);
        view.setEnableTaskNamedMenus(false);
        view.showTask(task);
    }

    public void onTaskPauseSelected() {
        if (!taskController.isTaskRunning()) {
            throw new IllegalStateException("Something went wrong!");
        }
        pauseAndSaveTask();
    }

    public void onTaskResetSelected(ClockTask task) {
        taskController.select(task);
        if (taskController.isTaskRunning()) {
            throw new IllegalStateException("Something went wrong!");
        }
        taskController.resetTask();
        taskController.saveTasks();
        taskController.select(null);
    }

    private void pauseAndSaveTask() {
        taskController.pauseTask();
        taskController.saveTasks();
        taskController.select(null);
        view.setEnableTaskPauseMenu(false);
        view.setEnableTaskNamedMenus(true);
        view.stopShowingTask();
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
