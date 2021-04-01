package ru.nsu.fit.group18214.naidenov;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        int workers = Integer.parseInt(args[0]);
        DatabaseProvider dp = new DatabaseProvider();
        dp.connect();
        dp.clearTable();
        dp.closeConnection();
        System.out.println("Delete completed");
        List<Thread> threadList = new ArrayList<>();

        Source source = new Source();
        for (int i = 0; i < workers; i++) {
            threadList.add(new Thread(new Worker(source)));
        }

        for (Thread w : threadList) {
            w.start();
        }
        XmlParser.parse(source);
        try {
            for (Thread w : threadList) {
                w.join();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());

        }
    }
}
