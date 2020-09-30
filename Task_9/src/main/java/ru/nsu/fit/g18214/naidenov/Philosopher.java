package ru.nsu.fit.g18214.naidenov;

import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {
    private Semaphore sem;
    private final Object leftFork;
    private final Object rightFork;

    public Philosopher(Semaphore sem, Object[] forks, int number) {
        this.sem = sem;
        if (number == 0) {
            leftFork = forks[0];
            rightFork = forks[4];

        } else {
            leftFork = forks[number - 1];
            rightFork = forks[number];
        }
    }

    private void eat() throws InterruptedException {
        Thread.sleep(1000);

    }

    private void think() throws InterruptedException {
        Thread.sleep(100);

    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sem.acquire();
                synchronized (leftFork) {
                    synchronized (rightFork) {
                        System.out.println(Thread.currentThread().getName() + " took forks");
                        eat();
                    }

                }
                System.out.println(Thread.currentThread().getName() + " finished to eat");
                sem.release();
                think();
            } catch (InterruptedException e) {
               break;
            }
        }
    }
}
