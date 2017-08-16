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
    
    public PomodoroController() {
        workTimer = new CountdownTimer("work", 5, TimerType.WORK_BLUE);
        restTimer = new CountdownTimer("rest", 1, TimerType.REST_YELLOW);
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
            switchTimers();
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

    public Node createNewTimer(TimerType timerType) {
        CountdownTimer timer = new CountdownTimer("work", 5, timerType);
        timers.add(timer);
        return timer.getNode();
    }

    public void onSwitchTimers(Consumer<CountdownTimer> consumer) {
        onSwitchTimersAction = consumer;
    }

    private void switchTimers() {
        CountdownTimer oldTimer = timers.get(currentTimerIndex);
        oldTimer.pause();
        oldTimer.reset();
        if (currentTimerIndex == timers.size() - 1) {
            currentTimerIndex = 0;
        } else {
            currentTimerIndex++;
        }
        CountdownTimer newTimer = timers.get(currentTimerIndex);
        onSwitchTimersAction.accept(newTimer);
        newTimer.start();
    }

}
