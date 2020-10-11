package ru.nsu.fit.group18214.naidenov;

import java.util.ArrayList;
import java.util.List;

public class Worker implements Runnable {

    private ArrayList list;
    private Task task;


    public Worker(ArrayList list, Task task) {
        this.list = list;
        this.task = task;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (list) {
                if (list.size() > 0) {
                    task.sortList(list);
                   // System.out.println("Sorted");
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
