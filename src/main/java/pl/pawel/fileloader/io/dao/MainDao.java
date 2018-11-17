package pl.pawel.fileloader.io.dao;

import pl.pawel.fileloader.io.entities.Customer;

import java.util.List;

public interface MainDao {

    void open();
    void close();
    List<Customer> getAllCustomers();
    void saveCustomer(Customer customer);

}
