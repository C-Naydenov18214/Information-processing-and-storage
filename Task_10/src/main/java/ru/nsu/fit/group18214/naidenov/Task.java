package ru.nsu.fit.group18214.naidenov;

public class Task {

    private boolean turn = true;

    public synchronized void printMessage(String message, boolean tern) {
        while (tern != this.turn) {
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println(message);
        this.turn = !turn;
        notify();
    }
}
