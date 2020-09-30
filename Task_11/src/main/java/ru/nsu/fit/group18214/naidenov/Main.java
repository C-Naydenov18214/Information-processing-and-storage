package ru.nsu.fit.group18214.naidenov;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args)  {
        Semaphore sem1 = new Semaphore(1,true);
        Semaphore sem2 = new Semaphore(1,true);

        Task task = new Task();
        Worker worker = new Worker(task,sem1,sem2);
        Thread secondThread = new Thread(worker);
        try{
            sem1.acquire();
            secondThread.start();
            for(int i = 0 ; i < 10; i++) {

                sem2.acquire();
                sem1.release();
                task.printMessage(Thread.currentThread().getName() + " A");

                sem2.release();
                sem1.acquire();
            }
        } catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
}
