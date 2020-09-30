package ru.nsu.fit.g18214.naidenov;

import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {

  private Semaphore sem;
  private final Semaphore leftFork;
  private final Semaphore rightFork;
  private final Object monitor;
  private boolean bothFree;

  public Philosopher(
      Semaphore sem, Semaphore[] forks, int number, Object monitor, boolean bothFree) {
    this.sem = sem;
    this.monitor = monitor;
    this.bothFree = bothFree;
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
    boolean left;
    boolean right = false;
    while (!Thread.currentThread().isInterrupted()) {
      try {
        sem.acquire();
        synchronized (monitor) {
          if (bothFree) {
            if ((left = leftFork.tryAcquire()) && (right = rightFork.tryAcquire())) {
              System.out.println(Thread.currentThread().getName() + " took forks");
              eat();
              System.out.println(Thread.currentThread().getName() + " finished to eat");
              leftFork.release();
              rightFork.release();
            } else {
                if(left){
                    leftFork.release();
                }
                if(right){
                    rightFork.release();
                }

            }
          }
        }

        if (leftFork.tryAcquire() && rightFork.tryAcquire()) {

          System.out.println(Thread.currentThread().getName() + " took forks");
          eat();
          System.out.println(Thread.currentThread().getName() + " finished to eat");
          leftFork.release();
          rightFork.release();
        }
        sem.release();
      } catch (InterruptedException e) {
        break;
      }
    }
  }
}
