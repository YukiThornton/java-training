//package clock;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.BiConsumer;
//import java.util.function.Consumer;
//import java.util.stream.Collectors;
//
//import javafx.scene.Node;
//import javafx.scene.paint.Color;
//
//public class PomodoroController {
//
//    private static final TimerType[] INITIAL_TIMER_TYPES = {TimerType.WORK_DEFAULT, TimerType.BREAK_DEFAULT};
//    private static final int INITIAL_TIMER_INDEX = 0;
//    static final TimerType INITIAL_TIMER_TYPE = INITIAL_TIMER_TYPES[INITIAL_TIMER_INDEX];
//
//    private static final int MAX_TIMER_COUNT = 10;
//
//    private List<CountdownTimer> timers;
//    private int currentTimerIndex;
//    private BiConsumer<CountdownTimer, CountdownTimer> onTimerFinishedAction;
//    private Consumer<CountdownTimer> onInvalidInputForMaxMinuteAction;
//    private Consumer<CountdownTimer> onInvalidInputForTimerNameAction;
//    private Consumer<CountdownTimer> onTimerDeleteBtnSelectedAction;
//    private Consumer<CountdownTimer> onTimerDeletedAction;
//    
//    public PomodoroController(Color bgColor) {
//        timers = new ArrayList<>();
//        for(TimerType timerType: INITIAL_TIMER_TYPES) {
//            timers.add(new CountdownTimer(timerType, bgColor));
//        }
//        currentTimerIndex = INITIAL_TIMER_INDEX;
//        timers.get(currentTimerIndex).select();
//    }
//
//    public Node[] getNodes() {
//        return timers.stream().map(timer -> timer.getNode()).toArray(Node[]::new);
//    }
//
//    public void start() {
//        timers.get(currentTimerIndex).start();
//    }
//
//    public void pause() {
//        timers.get(currentTimerIndex).pause();
//    }
//
//    public void reset() {
//        CountdownTimer timer = timers.get(currentTimerIndex);
//        if (timer.isActive()) {
//            throw new IllegalStateException();
//        }
//        timer.reset();
//    }
//
//    public void deselectCurrent() {
//        deselect(currentTimerIndex);
//    }
//
//    public void deselect(int index) {
//        timers.get(index).deselect();
//    }
//
//    public void selectNext() {
//        select(nextTimerIndex());
//    }
//
//    public void select(int index) {
//        currentTimerIndex = index;
//        timers.get(currentTimerIndex).select();
//    }
//
//    public void deselectActiveTimer() {
//        CountdownTimer timer = timers.get(currentTimerIndex);
//        timer.pause();
//        timer.reset();
//        timer.deselect();
//    }
//
////    public List<TimerReport> getReports() {
////        return timers.stream().map(timer -> timer.getReport()).collect(Collectors.toList());
////    }
////
//    public void clearAllHistory() {
//        timers.forEach(timer -> timer.clearHistory());
//    }
//
//    public void update() {
//        CountdownTimer timer = timers.get(currentTimerIndex);
//        switch(timer.checkAndUpdateIfNecessary()) {
//        case UPDATED:
//        case NO_CHANGE:
//        case OVER_MAXIMUM:
//            break;
//        case HIT_MAXIMUM:
//            onTimerFinishedAction.accept(timers.get(currentTimerIndex), timers.get(nextTimerIndex()));
//            break;
//        }
//    }
//
//    public boolean isActive() {
//        return timers.get(currentTimerIndex).isActive();
//    }
//
//    public boolean canAddMoreTimer() {
//        return timers.size() < MAX_TIMER_COUNT;
//    }
//
//    public boolean canDeleteTimer(CountdownTimer timer) {
//        if (timer.isActive()) {
//            return false;
//        }
//        if (timers.size() <= 2) {
//            return false;
//        }
//        int workCount = (int) timers.stream().filter(t -> t.getTimerType().isWorkTimer()).count();
//        if (workCount <= 1 && timer.getTimerType().isWorkTimer()) {
//            return false;
//        }
//        if (timers.size() - workCount <= 1 && timer.getTimerType().isBreakTimer()) {
//            return false;
//        }
//        return true;
//    }
//
//    public CountdownTimer currentTimer() {
//        return timers.get(currentTimerIndex);
//    }
//
//    public Node createNewTimer(TimerType timerType, Color bgColor) {
//        CountdownTimer timer = new CountdownTimer(timerType, bgColor);
//        timer.onTimerDeleteBtnSelected(onTimerDeleteBtnSelectedAction);
//        timer.onInvalidInputForMaxMinute(onInvalidInputForMaxMinuteAction);
//        timer.onInvalidInputForTimerName(onInvalidInputForTimerNameAction);
//        timers.add(timer);
//        return timer.getNode();
//    }
//
//    public void setVisibleOnDeleteBtn(boolean visibleAndManaged) {
//        timers.forEach(t -> t.setVisibleOnDeleteBtn(visibleAndManaged));
//    }
//
//    public void onTimerFinished(BiConsumer<CountdownTimer, CountdownTimer> consumer) {
//        onTimerFinishedAction = consumer;
//    }
//
//    public void onInvalidInputForMaxMinute(Consumer<CountdownTimer> consumer) {
//        onInvalidInputForMaxMinuteAction = consumer;
//        if (timers != null) {
//            timers.forEach(t -> {
//                t.onInvalidInputForMaxMinute(consumer);
//            });
//        }
//    }
//
//    public void onInvalidInputForTimerName(Consumer<CountdownTimer> consumer) {
//        onInvalidInputForTimerNameAction = consumer;
//        if (timers != null) {
//            timers.forEach(t -> {
//                t.onInvalidInputForTimerName(consumer);
//            });
//        }
//    }
//
//    public void onTimerDeleteBtnSelected(Consumer<CountdownTimer> consumer) {
//        onTimerDeleteBtnSelectedAction = consumer;
//        if (timers != null) {
//            timers.forEach(t -> {
//                t.onTimerDeleteBtnSelected(consumer);
//            });
//        }
//    }
//
//    public void deleteTimer(CountdownTimer timer) {
//        int index = timers.indexOf(timer);
//        if (index == currentTimerIndex) {
//            selectNext();
//        }
//        timers.remove(index);
//        if (index < currentTimerIndex) {
//            currentTimerIndex--;
//        }
//        if (onTimerDeletedAction != null) {
//            onTimerDeletedAction.accept(timer);
//        }
//    }
//
//    public void onTimerDeleted(Consumer<CountdownTimer> consumer) {
//        onTimerDeletedAction = consumer;
//    }
//
//    private int nextTimerIndex() {
//        if (currentTimerIndex == timers.size() - 1) {
//            return 0;
//        } else {
//            return currentTimerIndex + 1;
//        }
//    }
//}
