package ru.nsu.fit.g18214.naidenov;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable {
    private int n;
    private CyclicBarrier firstBarrier;
    private CyclicBarrier secondBarrier;
    private double res = 0;

    public Worker(CyclicBarrier firstBarrier,CyclicBarrier secondBarrier) {
        this.firstBarrier = firstBarrier;
        this.secondBarrier = secondBarrier;

    }

    public void updateN(int i) {
        n = i;
    }

    public double getRes() {
        return res;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                double tmp = Math.pow(-1, n) / (2 * n + 1);
                res+=tmp;

                firstBarrier.await();

                secondBarrier.await();
                //
            } catch (InterruptedException | BrokenBarrierException ex){
                System.out.println(Thread.currentThread().getName() + " interrupted");
                break;
            }
        }

    }
}
