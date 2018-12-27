package pl.pawel.fileloader.ui.impl;

import pl.pawel.fileloader.io.entities.Contact;
import pl.pawel.fileloader.io.entities.Customer;
import pl.pawel.fileloader.ui.FileConventer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvConverter implements FileConventer {
    public static final String PRODUCT_SEPARATOR = ",";

    @Override
    public List<Customer> convertFile(String fileName) {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Customer customer = createCustomer(line);
                customers.add(customer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private Customer createCustomer(String line) {
        String[] arrayOfCustomerAndContactsData = line.split(PRODUCT_SEPARATOR);
        String name = arrayOfCustomerAndContactsData[0];
        String surname = arrayOfCustomerAndContactsData[1];
        Integer age = null;
        if (!arrayOfCustomerAndContactsData[2].equals("")) {
            age = Integer.parseInt(arrayOfCustomerAndContactsData[2]);
        }
        Customer customer = new Customer(name, surname, age);
        addContactsToCustomer(arrayOfCustomerAndContactsData, customer);
        return customer;
    }

    private void addContactsToCustomer(String[] data, Customer customer) {
        int startOfContacts = 4;
        for (int i = startOfContacts; i < data.length; i++) {
            String contactData = data[i];
            int type = 0;
            if (contactData.contains("@")) {
                type = 1;
            }
            Contact contact = new Contact(type, contactData);
            customer.getContacts().add(contact);
        }
    }
}
