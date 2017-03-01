package jpl.ch14.ex03;

public class Sample {
    public static void main(String[] args){
        Counter counter = new Counter();
        Runnable service = new Runnable() {
            
            @Override
            public void run() {
                counter.show();
            }
        };
        new Thread(service, "Thread1").start();
        new Thread(service, "Thread2").start();
        new Thread(service, "Thread3").start();
        new Thread(service, "Thread4").start();
        new Thread(service, "Thread5").start();
        new Thread(service, "Thread6").start();
        new Thread(service, "Thread7").start();
    }
}
