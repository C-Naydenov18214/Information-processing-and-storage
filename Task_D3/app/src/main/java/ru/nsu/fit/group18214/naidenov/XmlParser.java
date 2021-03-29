package ru.nsu.fit.group18214.naidenov;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class XmlParser {


    public static void parse() throws FileNotFoundException, XMLStreamException {

        ArrayList<Person> persons = new ArrayList<>(20000);
        Person curPerson = null;
        String tagContent = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader =
                factory.createXMLStreamReader(
                        new FileInputStream("output.xml"));

        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String localName = reader.getLocalName();
                    if ("person".equals(localName)) {
                        curPerson = new Person();
                        String id = reader.getAttributeValue(0);
                        curPerson.setId(id);
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String contentTeg = reader.getLocalName();
                    switch (contentTeg) {
                        case "person":
                            persons.add(curPerson);
                            break;
                        case "gender":
                            curPerson.setGender(tagContent);
                            break;

                        case "firstname":
                            curPerson.setFirstName(tagContent);
                            break;

                        case "surname":
                            curPerson.setLastName(tagContent);
                            break;
                        case "mother":
                            curPerson.mother = tagContent;
                            break;
                        case "father":
                            curPerson.father = tagContent;
                            break;
                        case "sister":
                            curPerson.sisters.add(tagContent);
                            break;
                        case "brother":
                            curPerson.brothers.add(tagContent);
                            break;
                        case "daughter":
                            curPerson.daughters.add(tagContent);
                            break;
                        case "son":
                            curPerson.sons.add(tagContent);
                            break;
                        case "husband":
                            curPerson.husband = tagContent;
                            break;
                        case "wife":
                            curPerson.wife = tagContent;
                            break;
                    }
                    break;
                case XMLStreamConstants.START_DOCUMENT:

                    break;
            }

        }
        System.out.println("heh");

    }
}