package jpl.ch14.ex06;

import java.time.LocalTime;

public class Sample {
    public static void main(String[] args){
    
        StopWatch stopWatch = new StopWatch();
    
        Runnable showTimeService = new Runnable() {
            
            @Override
            public void run() {
                try {
                    stopWatch.start(LocalTime.now());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable showMessageEveyFifteenSeconds = new Runnable() {
            
            @Override
            public void run() {
                try {
                    stopWatch.showMessage(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable showMessageEveySevenSeconds = new Runnable() {
            
            @Override
            public void run() {
                try {
                    stopWatch.showMessage(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(showTimeService, "showTime").start();
        new Thread(showMessageEveySevenSeconds, "showTime").start();
        new Thread(showMessageEveyFifteenSeconds, "showTime").start();
    }
}
