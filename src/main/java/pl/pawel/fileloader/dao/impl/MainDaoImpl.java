package pl.pawel.fileloader.dao.impl;

import pl.pawel.fileloader.dao.MainDao;
import pl.pawel.fileloader.entities.Contact;
import pl.pawel.fileloader.entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainDaoImpl implements MainDao {

    private String url = "jdbc:postgresql:filereaderapp";
    private Connection conn;

    public void open() {
        try {
            conn = DriverManager.getConnection(url, "postgres", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {

        try (Statement statement = conn.createStatement()) {
            ResultSet results = statement.executeQuery("SELECT * FROM CUSTOMERS");

            List<Customer> customers = new ArrayList<>();
            while (results.next()) {
                Customer customer = new Customer();
                customer.setId(results.getLong(1));
                customer.setName(results.getString(2));
                customer.setSurname(results.getString(3));
                customer.setAge(results.getInt(4));
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void saveCustomer(Customer customer) {
        String query = "insert into customers values(?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setLong(1, customer.getId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getSurname());
            if (customer.getAge() !=null) {
                statement.setInt(4, customer.getAge());
            } else {
                statement.setObject(4, null);
            }
            statement.execute();
            statement.close();
            saveContacts(customer.getContacts());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void saveContacts(List<Contact> contacts) {
        String query = "insert into contacts values(?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (Contact contact : contacts) {
                statement.setLong(1, contact.getId());
                statement.setLong(2, contact.getIdCustomer());
                statement.setInt(3, contact.getType());
                statement.setString(4, contact.getContact());
                statement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
