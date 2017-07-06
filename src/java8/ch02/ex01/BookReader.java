package java8.ch02.ex01;

import java.util.ArrayList;
import java.util.List;

public class BookReader {

    private int counter = 0;
    Object countLock = new Object();

    public int countWordsLongerThanTwelve(List<String> words) {
        int count = 0;
        for (String w: words) {
            if (w.length() > 12) {
                count++;
            }
        }
        return count;
    }

    public int countWordsLongerThanTwelveParallel(List<String> words) throws InterruptedException {
        initCounter();
        List<List<String>> views = divideListView(words, 5);
        Thread[] threads = new Thread[views.size()];
        for (int i = 0; i < views.size(); i++) {
            List<String> view = views.get(i);
            threads[i] = new Thread(() -> {
                for (String w: view) {
                    if (w.length() > 12) {
                        countUp();
                    }
                }
            });
        }
        for (Thread thread: threads) {
            thread.start();
        }
        for (Thread thread: threads) {
            thread.join();
        }
        return counter;
    }
    
    private void initCounter() {
        counter = 0;
    }
    private void countUp() {
        synchronized(countLock) {
            counter++;
        }
    }
    private List<List<String>> divideListView(List<String> target, int maxSizeOfView) {
        if (target == null || maxSizeOfView < 0) {
            throw new IllegalArgumentException("Something wrong.");
        }
        List<List<String>> result = new ArrayList<>();
        int s = 0;
        while(true) {
            if (s + maxSizeOfView < target.size()) {
                result.add(target.subList(s, s + maxSizeOfView));
                s += maxSizeOfView;
            } else if (s + maxSizeOfView >= target.size()) {
                result.add(target.subList(s, target.size()));
                break;
            }
        }
        return result;
    }
}
