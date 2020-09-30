package ru.nsu.fit.g18214.naidenov;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        if (args.length < 1) {
            return;
        }
        int numberOfThreads = Integer.parseInt(args[0]);
        int numberOfIteration = 1000;

        ArrayList<Thread> threads = new ArrayList<>(numberOfThreads);
        ArrayList<Worker> workers = new ArrayList<>(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            workers.add(new Worker());
        }
        int n = 0;
        int i = 0;
        while (n < numberOfIteration) {
            if (i >= numberOfThreads) {
                i = 0;
            }
            workers.get(i).addN(n);
            i++;
            n++;
        }

        for (i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(workers.get(i)));
            threads.get(i).start();
        }
        try {
            for (i = 0; i < numberOfThreads; i++) {
                threads.get(i).join();
            }
        } catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }
        double res = 0;
        for (Worker w : workers) {
            int size = w.getResSize();
            for (int k = 0; k < size; k++) {
                res += w.getResN(k);
            }
        }

        System.out.println(res);
        System.out.println(res*4);

    }
}
