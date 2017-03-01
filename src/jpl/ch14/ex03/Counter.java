package jpl.ch14.ex03;

public class Counter {
    private int count = 0;
    
    public synchronized void show() {
        System.out.println(++count + " in " + Thread.currentThread().getName());            
    }
}
