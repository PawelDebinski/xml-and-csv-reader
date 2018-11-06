package pl.pawel.fileloader.dao;

import pl.pawel.fileloader.entities.Customer;

import java.util.List;

public interface MainDao {

    void open();
    void close();
    List<Customer> getAllCustomers();
    void saveCustomer(Customer customer);

}
