package jpl.ch14.ex02;

import java.util.LinkedList;
import java.util.Queue;

public class PrintServer implements Runnable {

    private final PrintQueue requests = new PrintQueue();
    private Thread thread;
    
    public PrintServer() {
        thread = new Thread(this);
        thread.start();
    }
    
    public void print(PrintJob job){
        requests.add(job);
    }

    @Override
    public void run() {
        checkThread();
        for(;;){
            realPrint(requests.remove());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void realPrint(PrintJob job) {
        if(job != null) {
            System.out.println(job);
        }
    }
    
    private void checkThread() {
        if (Thread.currentThread() != thread) {
            throw new IllegalStateException();
        }
    }
    
    static class PrintQueue {
    
        private Queue<PrintJob> queue = new LinkedList<>();
        
        private void add(PrintJob job) {
            queue.add(job);
        }
        
        private PrintJob remove() {
            return queue.poll();
        }
    }
    
    static class PrintJob {
        private String message;
        
        public PrintJob(String message) {
            this.message = message;
        }
        
        @Override
        public String toString() {
            return message;
        }
    }

}
