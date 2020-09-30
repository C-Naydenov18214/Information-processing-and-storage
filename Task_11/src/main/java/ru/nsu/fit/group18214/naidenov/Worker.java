package ru.nsu.fit.group18214.naidenov;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Worker implements Runnable {
    private Task task;

   private Semaphore sem1;
    private Semaphore sem2;
    public Worker(Task task, Semaphore sem1, Semaphore sem2) {
        this.task = task;

        this.sem1 = sem1;
       this.sem2 = sem2;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {

              sem1.acquire();
              sem2.acquire();
           } catch (InterruptedException e) {
               e.printStackTrace();
            }
            task.printMessage(Thread.currentThread().getName() + " B");

            sem1.release();
            sem2.release();
        }
    }
}
