package ru.nsu.fit.group18214.naidenov;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws JAXBException, SAXException, ParserConfigurationException, TransformerException, IOException {
        /*XmlParser parser = new XmlParser();

        Map<String, Person> map = null;
        try {
            map = parser.parse();
        } catch (XMLStreamException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        assert map != null;
        List<Person> persons = new ArrayList<>(map.values());
        XmlConfig xml = new XmlConfig();
        xml.setPersonList(persons);

        XmlWriter writer = new XmlWriter();
        writer.writeToXml(xml,"D:\\Java Projects\\Cybersecurity\\Information-processing-and-storage\\Task_XML\\output.xml");*/

        XsltWriter xsltWriter = new XsltWriter();

        xsltWriter.writeXSLT();



    }

}
