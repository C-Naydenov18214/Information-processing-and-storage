package ru.nsu.fit.g18214.naidenov;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Master implements Runnable {
    private int numberOfThreads;
    private ArrayList<Thread> threads;
    private ArrayList<Worker> workers;
    private double res = 0;
    private CyclicBarrier firstBarrier;
    private CyclicBarrier secondBarrier;
    private int n = 0;
    private int i = 0;

    public Master(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.threads = new ArrayList<>(numberOfThreads);
        this.workers = new ArrayList<>(numberOfThreads);
        this.firstBarrier = new CyclicBarrier(numberOfThreads + 1);
        this.secondBarrier = new CyclicBarrier(numberOfThreads + 1);
        for (i = 0; i < numberOfThreads; i++) {
            workers.add(new Worker(firstBarrier, secondBarrier));
        }
        for (i = 0; i < numberOfThreads; i++) {
            workers.get(i).updateN(n);
            n++;
        }


    }

    private void showRes(){
        System.out.println("MASTER IS INTERRUPTED");
        for (int j = 0; j < numberOfThreads; j++) {
            threads.get(j).interrupt();
        }
        for (int j = 0; j < numberOfThreads; j++) {
            try {
                threads.get(j).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Worker w : workers) {
            res += w.getRes();
        }
        System.out.println(res);
        System.out.println(res * 4);
        System.out.println("MASTER END");
    }

    @Override
    public void run() {
        for (i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(workers.get(i)));
            threads.get(i).start();
        }
        while (!Thread.currentThread().isInterrupted()) {
            try {
                try {
                    firstBarrier.await();
                    for (i = 0; i < numberOfThreads; i++) {
                        workers.get(i).updateN(n);
                        n++;
                    }
                    secondBarrier.await();
                } catch (BrokenBarrierException ex) {
                    System.exit(-168);
                }
            } catch (InterruptedException ex){
                System.out.println("Caught EXCEPTION");
                showRes();
                break;
            }

        }
    }
}
