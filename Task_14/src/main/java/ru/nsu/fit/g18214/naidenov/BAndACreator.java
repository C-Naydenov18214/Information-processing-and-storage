package ru.nsu.fit.g18214.naidenov;

import java.util.concurrent.Semaphore;

public class BAndACreator implements Runnable {
  private int numberOfDetails;
  private int timeToSleep;
  private ModuleMaker maker;
  private int mod;
  private Semaphore sem;

  public BAndACreator(int number, int time, int mod, ModuleMaker maker, Semaphore sem) {
    this.numberOfDetails = number;
    this.timeToSleep = time;
    this.maker = maker;
    this.mod = mod;
    this.sem = sem;
  }

  @Override
  public void run() {
    try {
      for (int i = 0; i < numberOfDetails; i++) {
        Thread.sleep(timeToSleep);
        sem.acquire();
        if (mod == 0) {
          maker.addA();
        } else if(mod == 1){
            maker.addB();
        }
        sem.release();
      }
    } catch (InterruptedException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
