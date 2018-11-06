package pl.pawel.fileloader.input;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import pl.pawel.fileloader.entities.Customer;

import java.util.List;

public interface XmlDataLoader {

    void loadXml(String fileName);
    void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException;
    void endElement(String uri, String localName, String qName) throws SAXException;
    void characters(char[] ch, int start, int length) throws SAXException;
    List<Customer> returnCustomerList(String fileName);

}
