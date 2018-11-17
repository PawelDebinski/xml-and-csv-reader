package pl.pawel.fileloader.io.dao.impl;

import pl.pawel.fileloader.io.dao.MainDao;
import pl.pawel.fileloader.io.entities.Contact;
import pl.pawel.fileloader.io.entities.Customer;
import pl.pawel.fileloader.config.PropertyLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainDaoImpl implements MainDao {

    private String url = PropertyLoader.getDriver();
    private String user = PropertyLoader.getDbUser();
    private String password = PropertyLoader.getDbPassword();
    private Connection conn;

    public void open() {
        try {
            conn = DriverManager.getConnection(url, user, password);
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
        createCustomersTable();
        createContactsTable();

        String query = "insert into customers(name, surname, age) values(?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getSurname());
            if (customer.getAge() !=null) {
                statement.setInt(3, customer.getAge());
            } else {
                statement.setObject(3, null);
            }
            statement.executeUpdate();
            ResultSet results = statement.getGeneratedKeys();
            int generatedId = 0;
            if (results.next()) {
                generatedId = results.getInt(1);
            }

            statement.close();
            saveContacts(customer.getContacts(), generatedId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void saveContacts(List<Contact> contacts, int customerId) {
        String query = "insert into contacts(id_customer, type, contact) values(?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (Contact contact : contacts) {
                statement.setLong(1, customerId);
                statement.setInt(2, contact.getType());
                statement.setString(3, contact.getContact());
                statement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createCustomersTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS customers" +
                "(id SERIAL PRIMARY KEY," +
                " name VARCHAR(50)," +
                " surname VARCHAR(50)," +
                " age INTEGER)";

        try {
            PreparedStatement statement = conn.prepareStatement(sqlCreateTable);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createContactsTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS contacts" +
                "(id SERIAL PRIMARY KEY," +
                " id_customer INTEGER," +
                " type INTEGER," +
                " contact VARCHAR(50))";

        try {
            PreparedStatement statement = conn.prepareStatement(sqlCreateTable);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
