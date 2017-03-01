package jpl.ch14.ex05;

public class Counter {
    private static int count = 0;
    
    public static void add() {
        synchronized (Counter.class) {
            System.out.println(count + "+1=" + ++count + " in " + Thread.currentThread().getName());            
        }
    }
    public static void subtract() {
        synchronized (Counter.class) {
            System.out.println(count + "-1=" + --count + " in " + Thread.currentThread().getName());            
        }
    }
}
