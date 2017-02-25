package jpl.ch14.ex04;

public class Sample {
    public static void main(String[] args){
        Runnable service = new Runnable() {
            
            @Override
            public void run() {
                Counter.show();
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
