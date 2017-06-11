package gui.ex23;

public class TaskController {

    private static final String TASK_SEPARATOR = ";";
    private ClockValues values;
    private ClockView view;
    private ClockTask[] tasks = null;
    private ClockTask selectedTask;

    public TaskController(ClockValues values, ClockView view) {
        this.values = values;
        this.view = view;
    }

    public ClockTask[] tasks() {
        this.tasks = analyzeTasks(values.taskString());
        System.out.println("Loaded->" + prefString());
        return tasks;
    }

    public void select(ClockTask task) {
        selectedTask = task;
    }

    public void saveTasks() {
        if (tasks == null) {
            throw new IllegalStateException("No tasks are available.");
        }
        values.saveTaskString(prefString());
        System.out.println("Saved->" + prefString());
    }

    public void startTask() {
        if (selectedTask == null) {
            throw new IllegalStateException("No task is selected.");
        }
        selectedTask.start();
    }

    public void pauseTask() {
        if (selectedTask == null) {
            throw new IllegalStateException("No task is selected.");
        }
        selectedTask.pause();
    }

    public void resetTask() {
        if (selectedTask == null) {
            throw new IllegalStateException("No task is selected.");
        }
        selectedTask.reset();
    }

    public void renameTask(String newName) {
        if (selectedTask == null) {
            throw new IllegalStateException("No task is selected.");
        }
        selectedTask.setName(newName);
    }

    public boolean isTaskRunning() {
        if (selectedTask == null || !selectedTask.isRunning()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return prefString();
    }

    public String prefString() {
        StringBuffer buffer = new StringBuffer();
        for (ClockTask task: tasks) {
            buffer.append(task.prefString());
            buffer.append(TASK_SEPARATOR);
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }

    private ClockTask[] analyzeTasks(String taskString) {
        tasks = new ClockTask[ClockValues.TASK_AMOUNT];
        String[] separateTaskStrings = taskString.split(TASK_SEPARATOR);
        if (separateTaskStrings.length != tasks.length) {
            throw new IllegalStateException("Something went wrong!");
        }
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = ClockTask.analyzeAndCreateTask(separateTaskStrings[i]);
        }
        return tasks;
    }

}
