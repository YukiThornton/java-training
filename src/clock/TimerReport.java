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
        long s = duration.getSeconds();
        StringBuilder sb = new StringBuilder(timerName);
        sb.append(" : ");
        sb.append(String.format("%dh %dmin %dsec", s / 3600, (s % 3600) / 60, (s % 60)));
        return sb.toString();
    }
    
}
