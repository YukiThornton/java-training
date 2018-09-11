package clock;

interface Timer {

    TimerType timerType();
    String timerName();
    void changeTimerName(String name);
    boolean isRunning();
    boolean isPaused();
    Values timeValues();
    void changeTargetTime(int seconds);
    void start();
    void pause();
    void clearCurrentSession();
    void clearAll();
    TimerReport report();

    interface Values {
        int targetTimeInSeconds();
        int timePassedInSeconds();
        int timeLeftInSeconds();
    }
}
