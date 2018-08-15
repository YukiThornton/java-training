package clock;

import java.util.ArrayList;
import java.util.List;

class AppState {

    private final List<Timer> timers;
    private final int currentTimerIndex;
    private final boolean deleteModeOn;

    static enum PomodoroStatus {
        NOT_SELECTED, SELECTED, RUNNING;
    }

    static AppState initState(TimerType[] timerTypes, int index) {
        List<Timer> timers = new ArrayList<>();
        for(TimerType type: timerTypes) {
            timers.add(CountdownTimer.create(type));
        }
        return new AppState(timers, index);
    }

    PomodoroStatus pomodoroStatus() {
        if (!timerSelected()) {
            return PomodoroStatus.NOT_SELECTED;
        }
        if (currentTimerIsRunning()) {
            return PomodoroStatus.RUNNING;
        }
        return PomodoroStatus.SELECTED;
    }

    int currentTimerIndex() {
        return currentTimerIndex;
    }

    Timer currentTimer() {
        return timers.get(currentTimerIndex);
    }

    List<Timer> timers() {
        return timers;
    }

    ColorPalette currentColorPalette() {
        return timers.get(currentTimerIndex).timerType().colorPalette();
    }

    AppState setTimerIndex(int index) {
        return new AppState(this, index);
    }

    AppState switchDeleteMode() {
        return new AppState(this, !this.deleteModeOn);
    }

    boolean isDeleteModeOn() {
        return deleteModeOn;
    }

    private AppState(List<Timer> timers, int index) {
        this.timers = timers;
        this.currentTimerIndex = index;
        this.deleteModeOn = false;
    }

    private AppState(AppState original, int index) {
        this.timers = original.timers;
        this.currentTimerIndex = index;
        this.deleteModeOn = original.deleteModeOn;
    }

    private AppState(AppState original, boolean deleteModeOn) {
        this.timers = original.timers;
        this.currentTimerIndex = original.currentTimerIndex;
        this.deleteModeOn = deleteModeOn;
    }

    private boolean currentTimerIsRunning() {
        return timers.get(currentTimerIndex).isRunning();
    }

    private boolean timerSelected() {
        return currentTimerIndex >= 0;
    }

}
