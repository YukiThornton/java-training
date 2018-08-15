package clock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CountdownTimer implements Timer {

    private final TimerType timerType;
    private String timerName;
    private int targetTimeInSeconds;
    private final List<Session> finishedSessions = new ArrayList<>();
    private Session currentSession = null;

    static CountdownTimer create(TimerType type) {
        return new CountdownTimer(type);
    }

    private CountdownTimer(TimerType type) {
        this.timerType = type;
        this.timerName = type.initialTimerName();
        this.targetTimeInSeconds = type.initialTimerMinute() * 60;
    }

    @Override
    public TimerType timerType() {
        return timerType;
    }

    @Override
    public String timerName() {
        return timerName;
    }

    @Override
    public void changeTimerName(String name) {
        if (isRunning()) {
            throw new IllegalStateException("Not allowed to modify values of running timer");
        }
        if (name == null || name.length() > LimitationValues.MAX_TIMER_NAME_LENGTH) {
            throw new IllegalArgumentException("Timer name invalid: " + name);
        }
        this.timerName = name;
    }

    @Override
    public boolean isRunning() {
        return hasCurrentSession() && !currentSession.paused();
    }

    private boolean hasCurrentSession() {
        return currentSession != null;
    }

    @Override
    public boolean isPaused() {
        return hasCurrentSession() && currentSession.paused();
    }

    private int timePassedInSeconds() {
        if (hasCurrentSession()) {
            return (int)currentSession.passedTimeInSession().getSeconds();
        }
        return 0;
    }

    @Override
    public Timer.Values timeValues() {
        return new Values(targetTimeInSeconds, timePassedInSeconds());
    }

    @Override
    public void changeTargetTime(int seconds) {
        if (isRunning()) {
            throw new IllegalStateException("Not allowed to modify values of running timer");
        }
        if (seconds < LimitationValues.MIN_TIMER_DURATION_TARGET * 60
                || seconds > LimitationValues.MAX_TIMER_DURATION_TARGET * 60
        ) {
            throw new IllegalArgumentException("Timer duration target invalid: " + seconds);
        }
        targetTimeInSeconds = seconds;
    }

    @Override
    public void start() {
        if (isRunning()) {
            throw new IllegalStateException("Aleady running.");
        }
        if (currentSession == null) {
            currentSession = new Session();
        } else {
            currentSession.resume();
        }
    }

    @Override
    public void pause() {
        if (!hasCurrentSession() || isPaused()) {
            throw new IllegalStateException("Timer is not running.");
        }
        currentSession.pause();
    }

    @Override
    public void clearCurrentSession() {
        if (!hasCurrentSession()) {
            throw new IllegalStateException("Timer does not have currentSession");
        }
        if (!isPaused()) {
            currentSession.pause();
        }
        finishedSessions.add(currentSession);
        currentSession = null;
    }

    @Override
    public void clearAll() {
        if (hasCurrentSession()) {
            clearCurrentSession();
        }
        finishedSessions.clear();
    }

    @Override
    public TimerReport report() {
        Duration totalPassedTimeOfSessions;
        if (hasCurrentSession()) {
            totalPassedTimeOfSessions = currentSession.passedTimeInSession();
        } else {
            totalPassedTimeOfSessions = Duration.ZERO;
        }
        for (Session session: finishedSessions) {
            totalPassedTimeOfSessions = totalPassedTimeOfSessions.plus(session.passedTimeInSession());
        }
        return new TimerReport(totalPassedTimeOfSessions, timerName);
    }

    private static class Values implements Timer.Values {

        private final int targetTimeInSeconds;
        private final int timePassedInSeconds;
        private final int timeLeftInSeconds;

        Values(int targetTimeInSeconds, int timePassedInSeconds) {
            this.targetTimeInSeconds = targetTimeInSeconds;
            this.timePassedInSeconds = timePassedInSeconds;
            this.timeLeftInSeconds = targetTimeInSeconds - timePassedInSeconds;
        }

        @Override
        public int targetTimeInSeconds() {
            return targetTimeInSeconds;
        }

        @Override
        public int timePassedInSeconds() {
            return timePassedInSeconds;
        }

        @Override
        public int timeLeftInSeconds() {
            return timeLeftInSeconds;
        }
    }

    private class Session {
        private final List<Block> finishedBlocks = new ArrayList<>();
        private Duration totalPassedTimeOfFinishedBlocks = Duration.ZERO;
        private Block currentBlock = null;

        Session() {
            currentBlock = new Block();
        }

        Duration passedTimeInSession() {
            if (currentBlock == null) {
                return totalPassedTimeOfFinishedBlocks;
            } else {
                return totalPassedTimeOfFinishedBlocks.plus(currentBlock.passedTimeInBlock());
            }
        }

        void pause() {
            currentBlock.finish();
            finishedBlocks.add(currentBlock);
            totalPassedTimeOfFinishedBlocks = totalPassedTimeOfFinishedBlocks.plus(currentBlock.passedTimeInBlock());
            currentBlock = null;
        }

        boolean paused() {
            return currentBlock == null;
        }

        void resume() {
            currentBlock = new Block();
        }
    }

    private class Block {
        private final LocalDateTime startedTime;
        private LocalDateTime finishedTime;
        private Duration duration;

        Block() {
            this.startedTime = LocalDateTime.now();
        }

        Duration passedTimeInBlock() {
            if (finishedTime == null) {
                return Duration.between(startedTime, LocalDateTime.now());
            }
            return duration;
        }

        void finish() {
            this.finishedTime = LocalDateTime.now();
            duration = Duration.between(startedTime, finishedTime);
        }
    }

}
