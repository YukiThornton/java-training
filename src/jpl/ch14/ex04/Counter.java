package jpl.ch14.ex04;

public class Counter {
    private static int count = 0;
    
    public synchronized static void show() {
        System.out.println(++count + " in " + Thread.currentThread().getName());            
    }
}
