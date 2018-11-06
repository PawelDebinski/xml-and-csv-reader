package pl.pawel.fileloader.input.impl;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pl.pawel.fileloader.entities.Contact;
import pl.pawel.fileloader.entities.Customer;
import pl.pawel.fileloader.input.XmlDataLoader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlDataLoaderImpl extends DefaultHandler implements XmlDataLoader {

    private String tempValue;
    private Long id = 1L;
    private Long contactId = 1L;
    private boolean insideContacts = false;

    private Customer customer;
    private Contact contact;
    private List<Customer> customers;
    private List<Contact> contacts;


    public XmlDataLoaderImpl() {
        customers = new ArrayList<>();
    }

    public List<Customer> returnCustomerList(String fileName) {
        System.out.println("inside returnCustomerList()");
        loadXml(fileName);
        return this.customers;
    }

    @Override
    public void loadXml(String fileName) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse("dane-osoby.xml", this);

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
        System.out.println("inside startElement(): " + qName.toString());
        if (qName.equalsIgnoreCase("persons")) {

        }
        if (qName.equalsIgnoreCase("person")) {
            customer = new Customer();
            customer.setId(id);
            contacts = new ArrayList<>();
            id++;
        }
        if (qName.equalsIgnoreCase("contacts")) {
            insideContacts = true;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("inside endElement(): " + qName.toString());
        if (qName.equals("persons")) {
            return;
        }
       if (qName.equals("person")) {
           customers.add(customer);
       }
       if (qName.equals("name")) {
           customer.setName(tempValue);
       }
       if (qName.equals("surname")) {
           customer.setSurname(tempValue);
       }
       if (qName.equals("age")) {
           customer.setAge(Integer.valueOf(tempValue));
       }
       if (qName.equals("city")) {

       }
       if (qName.equals("contacts")) {
           customer.setContacts(contacts);
           insideContacts = false;
       }

       if (insideContacts) {
           contact = new Contact();
           contact.setId(contactId);
           contact.setIdCustomer(customer.getId());

           if (qName.equals("phone")) {
               contact.setType(2);
               contact.setContact(tempValue);
           } else if (qName.equals("email")) {
               contact.setType(1);
               contact.setContact(tempValue);
           } else if (qName.equals("jabber")) {
               contact.setType(3);
               contact.setContact(tempValue);
           } else {
               contact.setType(0);
               contact.setContact(tempValue);
           }
           contactId++;
           contacts.add(contact);
       }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempValue = new String(ch, start, length);
        System.out.println("inside characters() - tempValue: " + tempValue.toString());
    }

}
