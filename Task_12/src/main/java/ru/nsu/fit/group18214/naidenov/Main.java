package ru.nsu.fit.group18214.naidenov;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>(30);
        Task task = new Task();
        Worker worker = new Worker(list, task);
        Scanner in = new Scanner(System.in);
        Thread thread = new Thread(worker);
        thread.start();
        try {
            while (true) {
                String str = in.nextLine();
                if (str.equals("end")) {
                    thread.interrupt();
                    break;
                }
                synchronized (list) {
                    if (str.equals("")) {
                        showList(list);
                    } else {
                        list.add(0, str);
                    }
                }
            }

            thread.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private static void showList(ArrayList<String> list) {
        int size = list.size();
        System.out.println("----------------------------------------");
        for (int i = 0; i < size; i++) {
            System.out.println(list.get(i));
        }
        System.out.println("----------------------------------------");
    }

}
