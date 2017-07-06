package gui.ex23;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.Timer;

public class ClockTask {

    private static final String SEPARATOR = ":";
    private static final String TASK_NAME_REGEX = "[a-zA-Z0-9_-]{1,10}+";
    private static Pattern taskNamePattern = Pattern.compile(TASK_NAME_REGEX);

    private String name;
    private int minute;
    private Timer timer;

    public ClockTask(String name, int minute) {
        this.name = name;
        this.minute = minute;
        timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMinute();
            }
        });
    }

    public static ClockTask analyzeAndCreateTask(String taskString) {
        String[] nameAndMinute = taskString.split(SEPARATOR);
        if (nameAndMinute.length != 2) {
            throw new IllegalStateException("Something went wrong!");
        }
        ClockTask task;
        try {
            task = new ClockTask(nameAndMinute[0], Integer.parseInt(nameAndMinute[1]));
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Something went wrong!");
        }
        return task;
    }

    public void start() {
        System.out.println("Start->" + name() + ":" + minute());
        timer.start();
    }

    public void pause() {
        if (!isRunning()) {
            throw new IllegalStateException("Something went wrong!");
        }
        System.out.println("Pause->" + name() + ":" + minute());
        timer.stop();
    }

    public void reset() {
        if (isRunning()) {
            throw new IllegalStateException("Something went wrong!");
        }
        setMinute(0);
        System.out.println("Reset->" + name() + ":" + minute());
    }

    public boolean isRunning() {
        return timer.isRunning();
    }

    @Override
    public String toString() {
        return prefString();
    }

    public String prefString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(name);
        buffer.append(SEPARATOR);
        buffer.append(minute);
        return buffer.toString();
    }

    public static boolean isValidTaskName(String name) {
        return taskNamePattern.matcher(name).matches();
    }

    public String name() {
        return name;
    }

    public String displayName() {
        return name + " : " + minute + "min";
    }

    public void setName(String name) {
        this.name = name;
    }

    public int minute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void addMinute() {
        minute++;
    }

}
