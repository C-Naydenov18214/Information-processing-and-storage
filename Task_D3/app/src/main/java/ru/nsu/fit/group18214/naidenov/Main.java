package ru.nsu.fit.group18214.naidenov;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        DatabaseProvider dp = new DatabaseProvider();
        dp.connect();
        dp.clearTable("daughters");
//        dp.clearTable("sons");
//        dp.clearTable("sisters");
//        dp.clearTable("brothers");
//        dp.clearTable("fathers");
//        dp.clearTable("mothers");
//        dp.clearTable("husbands");
//        dp.clearTable("wifes");
//        dp.clearTable("persons");
//        dp.closeConnection();
//        System.out.println("Delete completed");
//        List<Thread> threadList = new ArrayList<>();
//        int workers = 5;
//
//        Master master = new Master();
//        for (int i = 0; i < workers; i++) {
//            threadList.add(new Thread(new Worker(master)));
//        }
//
//        for (Thread w : threadList) {
//            w.start();
//        }
//        XmlParser.parse(master);
//        try {
//            for (Thread w : threadList) {
//                w.join();
//            }
//        } catch (InterruptedException ex) {
//            System.out.println(ex.getMessage());
//
//        }
    }
}
