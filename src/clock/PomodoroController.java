package clock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import clock.CountdownTimer.TimerPurpose;
import clock.CountdownTimer.TimerType;
import javafx.scene.Node;

public class PomodoroController {

    private static final int MAX_TIMER_COUNT = 10;

    private List<CountdownTimer> timers;
    private CountdownTimer workTimer;
    private CountdownTimer restTimer;
    private int currentTimerIndex;
    private BiConsumer<CountdownTimer, CountdownTimer> onSwitchTimersAction;
    private Consumer<CountdownTimer> onSelectNewTimerAction;
    private Consumer<CountdownTimer> onInvalidInputForMaxMinuteAction;
    private Consumer<CountdownTimer> onInvalidInputForTimerNameAction;
    private Consumer<CountdownTimer> onTimerDeleteBtnSelectedAction;
    private Consumer<CountdownTimer> onTimerDeletedAction;
    
    public PomodoroController() {
        workTimer = new CountdownTimer(TimerType.WORK_BLUE);
        restTimer = new CountdownTimer(TimerType.REST_YELLOW);
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

    public List<TimerReport> getReports() {
        return timers.stream().map(timer -> timer.getReport()).collect(Collectors.toList());
    }

    public void clearAllHistory() {
        timers.forEach(timer -> timer.clearHistory());
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

    public boolean canDeleteTimer(CountdownTimer timer) {
        if (timer.isActive()) {
            return false;
        }
        if (timers.size() <= 2) {
            return false;
        }
        int workCount = (int) timers.stream().filter(t -> t.getTimerPurpose() == TimerPurpose.WORK).count();
        if (workCount <= 1 && timer.getTimerPurpose() == TimerPurpose.WORK) {
            return false;
        }
        if (timers.size() - workCount <= 1 && timer.getTimerPurpose() == TimerPurpose.REST) {
            return false;
        }
        return true;
    }

    public CountdownTimer currentTimer() {
        return timers.get(currentTimerIndex);
    }

    public Node createNewTimer(TimerType timerType) {
        CountdownTimer timer = new CountdownTimer(timerType);
        timer.onTimerDeleteBtnSelected(onTimerDeleteBtnSelectedAction);
        timer.onInvalidInputForMaxMinute(onInvalidInputForMaxMinuteAction);
        timer.onInvalidInputForTimerName(onInvalidInputForTimerNameAction);
        timers.add(timer);
        return timer.getNode();
    }

    public void setVisibleAndMangedOnDeleteBtn(boolean visibleAndManaged) {
        timers.forEach(t -> t.setVisibleAndMangedOnDeleteBtn(visibleAndManaged));
    }

    public void onSwitchTimers(BiConsumer<CountdownTimer, CountdownTimer> consumer) {
        onSwitchTimersAction = consumer;
    }

    public void onSelectNewTimer(Consumer<CountdownTimer> consumer) {
        onSelectNewTimerAction = consumer;
    }

    public void onInvalidInputForMaxMinute(Consumer<CountdownTimer> consumer) {
        onInvalidInputForMaxMinuteAction = consumer;
        if (timers != null) {
            timers.forEach(t -> {
                t.onInvalidInputForMaxMinute(consumer);
            });
        }
    }

    public void onInvalidInputForTimerName(Consumer<CountdownTimer> consumer) {
        onInvalidInputForTimerNameAction = consumer;
        if (timers != null) {
            timers.forEach(t -> {
                t.onInvalidInputForTimerName(consumer);
            });
        }
    }

    public void onTimerDeleteBtnSelected(Consumer<CountdownTimer> consumer) {
        onTimerDeleteBtnSelectedAction = consumer;
        if (timers != null) {
            timers.forEach(t -> {
                t.onTimerDeleteBtnSelected(consumer);
            });
        }
    }

    public void deleteTimer(CountdownTimer timer) {
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
        if (onTimerDeletedAction != null) {
            onTimerDeletedAction.accept(timer);
        }
    }

    public void onTimerDeleted(Consumer<CountdownTimer> consumer) {
        onTimerDeletedAction = consumer;
    }

    public void switchAndStart() {
        switchTimers();
        timers.get(currentTimerIndex).start();
    }

    public void switchTimers() {
        CountdownTimer oldTimer = timers.get(currentTimerIndex);
        oldTimer.pause();
        if (currentTimerIndex == timers.size() - 1) {
            currentTimerIndex = 0;
        } else {
            currentTimerIndex++;
        }
        onSwitchTimersAction.accept(oldTimer, timers.get(currentTimerIndex));
        oldTimer.reset();
    }

    public void selectNewTimer(int index) {
        if (timers.get(currentTimerIndex).isActive()) {
            throw new IllegalStateException("Reset timer first.");
        }
        currentTimerIndex = index;
        onSelectNewTimerAction.accept(timers.get(currentTimerIndex));
    }

}
