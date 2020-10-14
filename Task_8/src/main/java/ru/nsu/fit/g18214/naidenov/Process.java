package ru.nsu.fit.g18214.naidenov;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Process {


    private Thread masterThread;
    public void runProcess(int numberOfThreads) {

        Master master = new Master(numberOfThreads);

        masterThread = new Thread(master);

       Thread signalThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("signalThread started");
                try {
                    Thread.sleep(1000);

                    Signal.raise(new Signal("INT"));
                    System.out.println("SIGINT");
                } catch (InterruptedException ignored) {

                }
            }
        });


        SignalHandler handler = new SignalHandler() {
            public void handle(Signal sig) {
                System.out.println("HANDLER START");
                masterThread.interrupt();
                System.out.println("INTERRUPTED THREAD");
                System.out.println("HANDLER END");
            }
        };

        Signal.handle(new Signal("INT"), handler);

        System.out.println("START");
        masterThread.start();
        signalThread.start();

        try {
            masterThread.join();
            System.out.println("masterThread.join()");
            signalThread.join();
            System.out.println("signalThread.join()");
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
       // System.exit(-168);
    }
}
