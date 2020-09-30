package ru.nsu.fit.group18214.naidenov;

public class ThreadCreator implements Runnable {
    @Override
    public void run() {
        for(int i = 0 ; i < 10;i++) {
            System.out.println(" AAA ");
        }
    }
}
