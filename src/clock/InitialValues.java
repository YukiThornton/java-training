package clock;

class InitialValues {
    static final TimerType[] TIMER_TYPES = {TimerType.WORK_DEFAULT, TimerType.BREAK_DEFAULT};
    static final int TIMER_INDEX = 0;
    static final ColorPalette COLOR_PALETTE = TIMER_TYPES[TIMER_INDEX].colorPalette();
}
