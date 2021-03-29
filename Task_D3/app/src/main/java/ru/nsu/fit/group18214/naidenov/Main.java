package ru.nsu.fit.group18214.naidenov;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        XmlParser.parse();
        DatabaseProvider.connect();
        DatabaseProvider.insertValues(XmlParser.persons);
        DatabaseProvider.closeConnection();
    }
}
