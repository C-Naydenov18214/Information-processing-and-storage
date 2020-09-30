package ru.nsu.fit.group18214.naidenov;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadCreator myThread = new ThreadCreator();
        Thread secondThread = new Thread(myThread);
        secondThread.start();
        secondThread.join();

        System.out.println("second thread finished");

        for (int i = 0; i < 10; i++){
            System.out.println(" BBB ");
        }


    }
}
