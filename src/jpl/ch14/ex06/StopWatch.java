package jpl.ch14.ex06;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StopWatch {

    private long secondCount = 0;
    
    public synchronized void start(LocalTime startingTime) throws InterruptedException {
        Duration duration;
        
        for (;;) {
            duration = Duration.between(startingTime, LocalTime.now());
            if(secondCount < duration.getSeconds()) {
                secondCount = duration.getSeconds();
                System.out.println(LocalTime.ofSecondOfDay(secondCount).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                notifyAll();
            }
            wait(100);
        }   
    }
    
    public synchronized void showMessage(int interval) throws InterruptedException {
        for (;;) {
            while (!(secondCount != 0 && secondCount % interval == 0)) {
                wait();
            }
            System.out.println("*********************" + interval + "*********************");
            wait(interval * 1000 - 500);
        }   
    }
}
