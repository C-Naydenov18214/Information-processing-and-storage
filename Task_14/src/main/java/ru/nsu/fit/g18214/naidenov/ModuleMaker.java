package ru.nsu.fit.g18214.naidenov;

import java.util.concurrent.Semaphore;

public class ModuleMaker implements Runnable {
  private int detailA = 0;
  private int detailB = 0;
  private WidgetMaker maker;
  private Semaphore semW;
  private Semaphore semM;
  private int number;

  public void addA() {

    detailA++;
  }

  public void addB() {
    detailB++;
  }

  public ModuleMaker(WidgetMaker maker, Semaphore semW, Semaphore semM, int number) {
    this.semW = semW;
    this.maker = maker;
    this.number = number;
    this.semM = semM;
    this.semW = semW;
  }

  @Override
  public void run() {

    int i = number;
    while (i != 0) {
      try {
        semM.acquire();
        if (detailA > 0 && detailB > 0) {
          detailA--;
          detailB--;
          semW.acquire();
          maker.addModule();
          semW.release();
          i--;
        }
        semM.release();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
