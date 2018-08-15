package clock;

import java.time.Duration;

public class TimerReport {

    private Duration duration;
    private String timerName;

    TimerReport(Duration duration, String timerName) {
        this.duration = duration;
        this.timerName = timerName;
    }

    public Duration duration() {
        return duration;
    }

    public String timerName() {
        return timerName;
    }

    @Override
    public String toString() {
        return timerName + ": " + duration.toMinutes() + "min";
    }
    
}
