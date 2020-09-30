package ru.nsu.fit.g18214.naidenov;

import java.util.concurrent.Semaphore;

public class CCreator implements  Runnable {
    private int numberOfDetails;
    private int timeToSleep;
    private WidgetMaker maker;
    private Semaphore sem;

    public CCreator(int number, int time, WidgetMaker maker, Semaphore sem){
        this.numberOfDetails = number;
        this.timeToSleep = time;
        this.maker = maker;
        this.sem = sem;
    }
    @Override
    public void run() {
        try {
            for (int i = 0; i < numberOfDetails; i++) {
                Thread.sleep(timeToSleep);
                sem.acquire();
                maker.addC();
                sem.release();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
