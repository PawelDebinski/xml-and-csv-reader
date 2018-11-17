package pl.pawel.fileloader.ui.impl;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pl.pawel.fileloader.io.entities.Contact;
import pl.pawel.fileloader.io.entities.Customer;
import pl.pawel.fileloader.ui.FileConventer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlConverter extends DefaultHandler implements FileConventer {

    private String tempValue;
    private boolean insideContacts = false;

    private Customer customer;
    private Contact contact;
    private List<Customer> customers;
    private List<Contact> contacts;


    public XmlConverter() {
        customers = new ArrayList<>();
    }

    public List<Customer> convertFile(String fileName) {
        loadXml(fileName);
        return this.customers;
    }

    private void loadXml(String fileName) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(fileName, this);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("persons")) {

        }
        if (qName.equalsIgnoreCase("person")) {
            customer = new Customer();
            contacts = new ArrayList<>();
        }
        if (qName.equalsIgnoreCase("contacts")) {
            insideContacts = true;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("persons")) {
            return;
        }
       if (qName.equalsIgnoreCase("person")) {
           customers.add(customer);
       }
       if (qName.equalsIgnoreCase("name")) {
           customer.setName(tempValue);
       }
       if (qName.equalsIgnoreCase("surname")) {
           customer.setSurname(tempValue);
       }
       if (qName.equalsIgnoreCase("age")) {
           customer.setAge(Integer.valueOf(tempValue));
       }
       if (qName.equalsIgnoreCase("city")) {

       }
       if (qName.equalsIgnoreCase("contacts")) {
           customer.setContacts(contacts);
           insideContacts = false;
       }

       if (insideContacts) {
           contact = new Contact();

           if (qName.equalsIgnoreCase("phone")) {
               contact.setType(2);
               contact.setContact(tempValue);
           } else if (qName.equalsIgnoreCase("email")) {
               contact.setType(1);
               contact.setContact(tempValue);
           } else if (qName.equalsIgnoreCase("jabber")) {
               contact.setType(3);
               contact.setContact(tempValue);
           } else {
               contact.setType(0);
               contact.setContact(tempValue);
           }
           contacts.add(contact);
       }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempValue = new String(ch, start, length);
    }

}
