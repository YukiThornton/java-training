package clock;

import java.util.ArrayList;
import java.util.List;

class AppState {

    private final List<TimerState> timerStates;
    private final int currentTimerIndex;
    private final boolean deleteModeOn;

    static enum PomodoroStatus {
        NOT_SELECTED, SELECTED, RUNNING;
    }

    static AppState initState(TimerType[] timerTypes, int index) {
        List<TimerState> timerStates = new ArrayList<>();
        for(TimerType type: timerTypes) {
            timerStates.add(new TimerState(type));
        }
        return new AppState(timerStates, index);
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

    TimerState currentTimer() {
        return timerStates.get(currentTimerIndex);
    }

    List<TimerState> timerStates() {
        return timerStates;
    }

    ColorPalette currentColorPalette() {
        return timerStates.get(currentTimerIndex).colorPalette();
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

    private AppState(List<TimerState> timerStates, int index) {
        this.timerStates = timerStates;
        this.currentTimerIndex = index;
        this.deleteModeOn = false;
    }

    private AppState(AppState original, int index) {
        this.timerStates = original.timerStates;
        this.currentTimerIndex = index;
        this.deleteModeOn = original.deleteModeOn;
    }

    private AppState(AppState original, boolean deleteModeOn) {
        this.timerStates = original.timerStates;
        this.currentTimerIndex = original.currentTimerIndex;
        this.deleteModeOn = deleteModeOn;
    }

    private boolean currentTimerIsRunning() {
        return timerStates.get(currentTimerIndex).isRunning();
    }

    private boolean timerSelected() {
        return currentTimerIndex >= 0;
    }

}
