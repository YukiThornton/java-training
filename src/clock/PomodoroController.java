package clock;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;

public class PomodoroController {

    private List<CountdownTimer> timers;
    private CountdownTimer workTimer;
    private CountdownTimer restTimer;
    private int currentTimerIndex;

    public PomodoroController() {
        workTimer = new CountdownTimer("work", 5, ColorSet.BLUE);
        restTimer = new CountdownTimer("rest", 1, ColorSet.YELLOW);
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

    private void switchTimers() {
        CountdownTimer oldTimer = timers.get(currentTimerIndex);
        oldTimer.pause();
        oldTimer.reset();
        if (currentTimerIndex == timers.size() - 1) {
            currentTimerIndex = 0;
        } else {
            currentTimerIndex++;
        }
        timers.get(currentTimerIndex).start();
    }

}
