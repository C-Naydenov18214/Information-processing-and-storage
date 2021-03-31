package ru.nsu.fit.group18214.naidenov;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public class Worker implements Runnable {

    private long seek;
    private String filename;

    public Worker(long seek, String filename) {
        this.seek = seek;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            XmlParser.parse(this.seek);
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
