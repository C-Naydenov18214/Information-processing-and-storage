package ru.nsu.fit.g18214.naidenov;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        int i = 0;
        boolean bothFree = true;
        Object monitor = new Object();
        ArrayList<Thread> threads = new ArrayList<>(5);
        Semaphore[] forks = new Semaphore[5];
        for (i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1, true);
        }
        Semaphore sem = new Semaphore(2, true);
        for (i = 0; i < 5; i++) {
            Thread thread = new Thread(new Philosopher(sem, forks, i,monitor,bothFree));
            threads.add(thread);
            threads.get(i).start();
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ex) {
            System.out.print(ex.getMessage());
        }
        for (i = 0; i < 5; i++) {
            threads.get(i).interrupt();
        }

        for (i = 0; i < 5; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException ex) {
                System.out.print(ex.getMessage());
            }
        }


    }
}
