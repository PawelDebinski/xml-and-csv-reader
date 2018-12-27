package pl.pawel.fileloader.io.dao.impl;

import pl.pawel.fileloader.io.dao.MainDao;
import pl.pawel.fileloader.io.entities.Contact;
import pl.pawel.fileloader.io.entities.Customer;
import pl.pawel.fileloader.config.PropertyLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainDaoImpl implements MainDao {
    private static MainDaoImpl instance = new MainDaoImpl();
    private String url = PropertyLoader.getDriver();
    private String user = PropertyLoader.getDbUser();
    private String password = PropertyLoader.getDbPassword();
    private Connection conn;

    private MainDaoImpl() {
        open();
        createCustomersTable();
        createContactsTable();
    }

    public static MainDaoImpl getInstance() {
        return instance;
    }

    public void open() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Customer> getAllCustomers() {
        try (Statement statement = conn.createStatement()) {
            ResultSet results = statement.executeQuery("select * from customers");
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
        String query = "insert into customers(name, surname, age) values(?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getSurname());
            if (customer.getAge() != null) {
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
        String query = "create table if not exists customers" +
                "(id serial primary key," +
                " name varchar(50)," +
                " surname varchar(50)," +
                " age integer)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createContactsTable() {
        String query = "create table if not exists contacts" +
                "(id serial primary key," +
                " id_customer integer," +
                " type integer," +
                " contact varchar(50))";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
