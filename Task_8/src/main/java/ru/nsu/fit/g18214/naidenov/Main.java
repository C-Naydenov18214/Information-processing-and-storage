package ru.nsu.fit.g18214.naidenov;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Main {


    public static void main(String[] args) {
        if (args.length < 1) {
            return;
        }

        int numberOfThreads = Integer.parseInt(args[0]);
        Process calculation = new Process();
        calculation.runProcess(numberOfThreads);
        System.out.println("MAIN END");
    }
}
