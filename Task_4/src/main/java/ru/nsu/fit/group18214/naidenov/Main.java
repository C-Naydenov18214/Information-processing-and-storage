package ru.nsu.fit.group18214.naidenov;



public class Main {
    public static void main(String[] args) {
        Worker worker = new Worker();

        Thread thread = new Thread(worker);

        thread.start();
        try {
            Thread.sleep(4000);
            thread.interrupt();
            thread.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
