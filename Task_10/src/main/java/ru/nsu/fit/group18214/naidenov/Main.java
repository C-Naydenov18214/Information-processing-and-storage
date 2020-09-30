package ru.nsu.fit.group18214.naidenov;

public class Main {
    public static void main(String[] args) {
        Task task = new Task();
        Worker worker = new Worker(task);
        Thread secondThread = new Thread(worker);
        secondThread.start();
        for (int i = 0; i < 10; i++) {
            task.printMessage(Thread.currentThread().getName() + " A", true);
        }
        try {
            secondThread.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());

        }
    }
}
