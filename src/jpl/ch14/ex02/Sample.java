package jpl.ch14.ex02;

import jpl.ch14.ex02.PrintServer.PrintJob;

public class Sample {

    public static void main(String[] args) {
        PrintJob job1 = new PrintJob("Job1");
        PrintJob job2 = new PrintJob("Job2");
        PrintServer server = new PrintServer();
        
        System.out.println("print success");
        server.print(job1);
        server.print(job2);
        
        Runnable service = new Runnable() {
            
            @Override
            public void run() {
                try{
                    server.run();
                } catch (IllegalStateException e) {
                    System.out.println("run fail");
                }
            }
        };
        new Thread(service).start();
    }

}
