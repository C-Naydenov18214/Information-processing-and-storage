package ru.nsu.fit.group18214.naidenov;

public class Worker implements Runnable {

    private Task task;

    public Worker(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            task.printMessage(Thread.currentThread().getName() + " B", false);
        }
    }
}
