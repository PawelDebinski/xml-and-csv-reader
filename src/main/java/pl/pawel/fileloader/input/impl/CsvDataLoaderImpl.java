package pl.pawel.fileloader.input.impl;

import pl.pawel.fileloader.entities.Contact;
import pl.pawel.fileloader.entities.Customer;
import pl.pawel.fileloader.input.CsvDataLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvDataLoaderImpl implements CsvDataLoader {

    public static final String PRODUCT_SEPARATOR = ",";

    private Long id = 1L;
    private Long contactId = 1L;

    @Override
    public List<Customer> loadCsv(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)))) {
            List<Customer> customers = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                String[] data = line.split(PRODUCT_SEPARATOR);
                String name = data[0];
                String surname = data[1];
                Integer age = null;
                if (!data[2].equals("")) {
                    age = Integer.parseInt(data[2]);
                }
                Customer customer = new Customer(id, name, surname, age);

                for (int i = 4; i < data.length; i++) {
                    Long idCustomer = id;
                    String contactData = data[i];
                    int type = 0;
                    if (contactData.contains("@")) {
                        type = 1;
                    }
                    Contact contact = new Contact(contactId, idCustomer, type, contactData);
                    customer.getContacts().add(contact);
                    contactId++;
                }
                customers.add(customer);
                id++;

            }

            return customers;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
