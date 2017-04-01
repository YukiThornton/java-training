package jpl.ch17.ex01;

import java.util.ArrayList;
import java.util.List;

public class MemoryChecker {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        printMemoryInfo("Start", runtime);

        List<Object> list = new ArrayList<>();
        addObjects(list, 99999);
        printMemoryInfo("After adding 100000 objects", runtime);

        addObjects(list, 100000);
        printMemoryInfo("After adding 200000 objects", runtime);

        addObjects(list, 100000);
        printMemoryInfo("After adding 300000 objects", runtime);
        
        list = null;
        
        runtime.gc();
        printMemoryInfo("After gc()", runtime);
    }
    
    private static void printMemoryInfo(String status, Runtime runtime) {
        System.out.println(status + ": " + runtime.freeMemory() + " / " + runtime.totalMemory());
    }
    
    private static void addObjects(List<Object> to, int amount) {
        for (int i = 0; i < amount; i++) {
            to.add(new Object());
        }
    }

}
