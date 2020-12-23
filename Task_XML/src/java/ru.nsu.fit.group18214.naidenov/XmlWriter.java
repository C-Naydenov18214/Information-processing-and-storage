package ru.nsu.fit.group18214.naidenov;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class XmlWriter {
    public void writeToXml(XmlConfig persons, String path) throws SAXException, JAXBException {
        JAXBContext jc = JAXBContext.newInstance(XmlConfig.class);
        Marshaller writer = jc.createMarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File schemaFile = new File ("schema1.xsd");
        writer.setSchema(schemaFactory.newSchema(schemaFile));
        writer.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        writer.marshal(persons, new File(path));


    }

    public void writeTest(XmlConfig persons, String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlConfig.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);



        //Marshal the employees list in file
        jaxbMarshaller.marshal(persons, new File(path));


    }
}
