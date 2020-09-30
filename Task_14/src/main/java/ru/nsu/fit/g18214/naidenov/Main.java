package ru.nsu.fit.g18214.naidenov;

import java.util.concurrent.Semaphore;

public class Main {

  public static void main(String[] args) {
    int numberOfDetails = 100;
    Semaphore semW = new Semaphore(1, true);
    Semaphore semM = new Semaphore(1, true);
    WidgetMaker widgetMaker = new WidgetMaker(numberOfDetails,semW);
    ModuleMaker moduleMaker = new ModuleMaker(widgetMaker, semW,semM, numberOfDetails);
    CCreator cCreator = new CCreator(numberOfDetails, 300, widgetMaker, semW);

    BAndACreator aCreator = new BAndACreator(numberOfDetails, 100, 0, moduleMaker, semM);
    BAndACreator bCreator = new BAndACreator(numberOfDetails, 200, 1, moduleMaker, semM);

    Thread threadA = new Thread(aCreator);
    threadA.start();
    Thread threadB = new Thread(bCreator);
    threadB.start();
    Thread threadC = new Thread(cCreator);
    threadC.start();
    Thread threadM = new Thread(moduleMaker);
    threadM.start();
    Thread threadW = new Thread(widgetMaker);
    threadW.start();
    try {
      threadA.join();
      threadB.join();
      threadC.join();
      threadM.join();
      threadW.join();
    } catch (InterruptedException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
