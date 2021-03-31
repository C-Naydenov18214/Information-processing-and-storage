package ru.nsu.fit.group18214.naidenov;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {

        DatabaseProvider.connect();
        DatabaseProvider.clearTable("persons");
        XmlParser.parse(0);
        DatabaseProvider.insertValues(XmlParser.persons);
        DatabaseProvider.closeConnection();
    }
}
