package jpl.ch14.ex05;

public class Sample {
    public static void main(String[] args){
        Runnable addingService = new Runnable() {
            
            @Override
            public void run() {
                Counter.add();
            }
        };
        Runnable subtractingService = new Runnable() {
            
            @Override
            public void run() {
                Counter.subtract();
            }
        };
        new Thread(addingService, "Thread1").start();
        new Thread(subtractingService, "Thread2").start();
        new Thread(addingService, "Thread3").start();
        new Thread(subtractingService, "Thread4").start();
        new Thread(addingService, "Thread5").start();
        new Thread(subtractingService, "Thread6").start();
        new Thread(addingService, "Thread7").start();
        new Thread(subtractingService, "Thread8").start();
        new Thread(addingService, "Thread9").start();
        new Thread(subtractingService, "Thread10").start();
        new Thread(addingService, "Thread11").start();
        new Thread(subtractingService, "Thread12").start();
        new Thread(addingService, "Thread13").start();
        new Thread(subtractingService, "Thread14").start();
    }
}
