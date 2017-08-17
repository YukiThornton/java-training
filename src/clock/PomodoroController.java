package clock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import clock.CountdownTimer.TimerType;
import javafx.scene.Node;

public class PomodoroController {

    private static final int MAX_TIMER_COUNT = 10;

    private List<CountdownTimer> timers;
    private CountdownTimer workTimer;
    private CountdownTimer restTimer;
    private int currentTimerIndex;
    private Consumer<CountdownTimer> onSwitchTimersAction;
    private Consumer<CountdownTimer> timerDeleteAction;
    private Consumer<CountdownTimer> onTimerDeleted;
    
    public PomodoroController() {
        timerDeleteAction = timer -> {
            if (timer.isActive() || timers.size() <= 2) {
                throw new IllegalStateException();
            }
            int index = timers.indexOf(timer);
            System.out.println(timer + " " + index);
            if (index == currentTimerIndex) {
                System.out.println("switch " + currentTimerIndex + " " + index);
                switchTimers();
            }
            timers.remove(index);
            if (index < currentTimerIndex) {
                currentTimerIndex--;
                System.out.println("minus " + currentTimerIndex + " " + index);
            }
            timers.forEach(t -> System.out.println(t));
            onTimerDeleted.accept(timer);
        };
        workTimer = new CountdownTimer("work", 5, TimerType.WORK_BLUE, timerDeleteAction);
        restTimer = new CountdownTimer("rest", 1, TimerType.REST_YELLOW, timerDeleteAction);
        timers = new ArrayList<>();
        timers.add(workTimer);
        timers.add(restTimer);
        currentTimerIndex = 0;
    }

    public Node[] getNodes() {
        return timers.stream().map(timer -> timer.getNode()).toArray(Node[]::new);
    }

    public void start() {
        timers.get(currentTimerIndex).start();
    }

    public void pause() {
        timers.get(currentTimerIndex).pause();
    }

    public void reset() {
        timers.get(currentTimerIndex).reset();
    }

    public void update() {
        CountdownTimer timer = timers.get(currentTimerIndex);
        switch(timer.checkAndUpdateIfNecessary()) {
        case UPDATED:
        case NO_CHANGE:
            break;
        case REACHED_MAXIMUM:
            switchAndStart();
            break;
        }
    }

    public boolean isActive() {
        return timers.get(currentTimerIndex).isActive();
    }

    public boolean canAddMoreTimer() {
        return timers.size() < MAX_TIMER_COUNT;
    }

    public CountdownTimer currentTimer() {
        return timers.get(currentTimerIndex);
    }

    public Node createNewTimer(String timerName, int maxMin, TimerType timerType) {
        CountdownTimer timer = new CountdownTimer(timerName, maxMin, timerType, timerDeleteAction);
        timers.add(timer);
        return timer.getNode();
    }

    public void onSwitchTimers(Consumer<CountdownTimer> consumer) {
        onSwitchTimersAction = consumer;
    }

    public void onTimerDeleted(Consumer<CountdownTimer> consumer) {
        onTimerDeleted = consumer;
    }

    public void switchAndStart() {
        switchTimers();
        timers.get(currentTimerIndex).start();
    }

    public void switchTimers() {
        CountdownTimer oldTimer = timers.get(currentTimerIndex);
        oldTimer.pause();
        oldTimer.reset();
        if (currentTimerIndex == timers.size() - 1) {
            currentTimerIndex = 0;
        } else {
            currentTimerIndex++;
        }
        onSwitchTimersAction.accept(timers.get(currentTimerIndex));
    }

}
