package ru.nsu.fit.g18214.naidenov;

import java.util.concurrent.Semaphore;

public class WidgetMaker implements Runnable {
  private int module = 0;
  private int cDetail = 0;
  private int numberOfDetails;
  private int widget = 0;
  private Semaphore sem;

  public void addModule() {
    module++;
  }

  public void addC() {
    cDetail++;
  }

  public WidgetMaker(int number, Semaphore sem) {
    this.numberOfDetails = number;
    this.sem = sem;
  }

  @Override
  public void run() {
    int i = numberOfDetails;
    while (i != 0) {
      try {
        sem.acquire();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (module > 0 && cDetail > 0) {
        module--;
        cDetail--;
        widget++;
        i--;
        System.out.println("Widget " + widget + " done");
      }
      sem.release();
    }
  }
}
