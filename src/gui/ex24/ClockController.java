package gui.ex24;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockController {
    private ClockView view;
    private ClockValues values;
    private TaskController taskController;
    private boolean isInitialized = false;
    private boolean changingTheme = false;
    private ClockMode currentMode = ClockMode.CLOCK;
    private ClockTask[] tasks = null;
    private MenuPreferenceActionListener menuPreferenceActionListener;
    
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
        this.menuPreferenceActionListener = new MenuPreferenceActionListener();
        view.init(currentMode, values, this);
        taskController = new TaskController(values, view);
        isInitialized = true;
    }
    
    public void start() {
        view.show();
    }
    
    public ActionListener getActionListnerForMenuPreference() {
        return menuPreferenceActionListener;
    }
    
    public void onValuesChanged(ClockValues newValues) {
        values = newValues;
    }

    public void onWindowClosing() {
        values.saveFrameBounds(view.getFrameBounds());
        values.saveViewPref();
        System.exit(0);
    }

    private void showPreferenceDialog() {
        view.showPreferenceDialog(values);
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
        view.showTaskMode();
    }

    private void quitTaskMode() {
        if (taskController.isTaskRunning()) {
            pauseAndSaveTask();
        }
        view.stopShowingTaskMode();
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

    public void onTaskRenameSelected(ClockTask task) {
        taskController.select(task);
        if (taskController.isTaskRunning()) {
            throw new IllegalStateException("Something went wrong!");
        }
        String newName = null;
        String initialVal = task.name();
        String message = ClockValues.DIALOG_MESSAGE_RENAME_TASK;
        do {
            newName = view.showTaskRenameDialog(message, task.name(), initialVal);
            message = ClockValues.DIALOG_MESSAGE_RENAME_TASK_AFTER_VALID_ERR;
            initialVal = newName;
        } while(newName != null && !isValidTaskName(newName));
        if (newName != null) {
            taskController.renameTask(newName);
            taskController.saveTasks();
        }
        taskController.select(null);
        view.updateNamesOfTaskNamedMenus(tasks);
    }

    private boolean isValidTaskName(String name) {
        return ClockTask.isValidTaskName(name);
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

    class MenuPreferenceActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            showPreferenceDialog();
        }
    
    }

}
