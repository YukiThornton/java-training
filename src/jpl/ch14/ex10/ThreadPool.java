/*
 * Copyright (C) 2012, 2013, 2016 RICOH Co., Ltd. All rights reserved.
 */
package jpl.ch14.ex10;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Simple Thread Pool class.
 *
 * This class can be used to dispatch an Runnable object to
 * be exectued by a thread.
 *
 * [Instruction]
 *  Implement one constructor and three methods.
 *  Don't forget to write a Test program to test this class. 
 *  Pay attention to @throws tags in the javadoc.
 *  If needed, you can put "synchronized" keyword to methods.
 *  All classes for implementation must be private inside this class.
 *  Don't use java.util.concurrent package.
 */
public class ThreadPool {
    private final TaskQueue queue;
    private boolean shouldCancelThreads = false;
    private Thread[] threads;
    private TaskChecker[] taskCheckers;
    private final ThreadGroup threadGroup;
    private static final String THREAD_GROUP_NAME = "thread pool 1";
    
    /**
     * Constructs ThreadPool.
     *
     * @param queueSize the max size of queue
     * @param numberOfThreads the number of threads in this pool.
     *
     * @throws IllegalArgumentException if either queueSize or numberOfThreads
     *         is less than 1
     */
    public ThreadPool(int queueSize, int numberOfThreads) {
        if(queueSize < 1 || numberOfThreads < 1){
            throw new IllegalArgumentException("Either queueSize or numberOfThreads is less than 1");
        }
        queue = new TaskQueue(queueSize);
        threads = new Thread[numberOfThreads];
        taskCheckers = new TaskChecker[numberOfThreads];
        threadGroup = new ThreadGroup(THREAD_GROUP_NAME);
        for(int i = 0; i < numberOfThreads; i++){
            taskCheckers[i] = new TaskChecker();
            threads[i] = new Thread(threadGroup, taskCheckers[i], "Thread" + i);
        }
    }

    /**
     * Starts threads.
     *
     * @throws IllegalStateException if threads has been already started.
     */
    public void start() {
        if(threadGroup.activeCount() > 0){
            throw new IllegalStateException();
        }
        for(Thread thread: threads){
            if(thread.isAlive()){
                throw new IllegalStateException();
            } else {
                thread.start();
            }
        }
        try {
            while (!allThreadsRunning()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return;
    }
    private boolean allThreadsRunning(){
        for(TaskChecker taskChecker: taskCheckers) {
            if (!taskChecker.taskCheckerRunning){
                return false;
            }
        }
        return true;
    }

    private boolean allThreadsCancelled(){
        for (Thread thread: threads) {
            if (thread.isAlive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Stop all threads and wait for their terminations.
     *
     * @throws IllegalStateException if threads has not been started.
     */
    public void stop() {
        if (!allThreadsRunning()) {
            throw new IllegalStateException("This pool has not been started yet.");
        }
        shouldCancelThreads = true;
        synchronized (queue) {
            queue.notifyAll();            
        }
        try {
            while (!allThreadsCancelled()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes the specified Runnable object, using a thread in the pool.
     * run() method will be invoked in the thread. If the queue is full, then
     * this method invocation will be blocked until the queue is not full.
     * 
     * @param runnable Runnable object whose run() method will be invoked.
     *
     * @throws NullPointerException if runnable is null.
     * @throws IllegalStateException if this pool has not been started yet.
     */
    public synchronized void dispatch(Runnable runnable) {
        if(runnable == null){
            throw new NullPointerException("runnable is null");
        }
        if (!allThreadsRunning()) {
            throw new IllegalStateException("This pool has not been started yet.");
        }
        queue.enqueue(runnable);
    }
    
    private class TaskChecker implements Runnable {
        boolean taskCheckerRunning = false;
        
        @Override
        public void run() {
            taskCheckerRunning = true;
            while (true) {
                Runnable task = queue.dequeue();
                if (task == null) {
                    taskCheckerRunning = false;
                    return;
                }
                task.run();
            }
        }
    
    }
    
    private class TaskQueue {
        private Queue<Runnable> queue = new LinkedList<>();
        private final int maxSize;
        
        TaskQueue(int size) {
            this.maxSize = size;
        }
        
        synchronized void enqueue(Runnable task) {
            if (task == null) {
                throw new IllegalArgumentException();
            }
            try {
                while (queue.size() == maxSize && !shouldCancelThreads) {
                    wait();
                } 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (shouldCancelThreads) {
                return;
            }
            queue.offer(task);                
            if (queue.size() > 0) {
                notifyAll();
            }            
        }
        synchronized Runnable dequeue(){
            Runnable task;
            try {
                while (queue.size() == 0 && !shouldCancelThreads) {
                    wait();
                }
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (shouldCancelThreads) {
                return null;
            }
            task = queue.poll();                
            if(queue.size() < maxSize) {
                notifyAll();
            }
            return task;
        }
    }
}
