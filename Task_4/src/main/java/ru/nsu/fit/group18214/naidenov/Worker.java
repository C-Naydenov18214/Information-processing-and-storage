package ru.nsu.fit.group18214.naidenov;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Worker implements Runnable {

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            String time = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss").format(Calendar.getInstance().getTime());
            System.out.println(time);
        }
        System.out.println("I'm interrupted");
    }
}
