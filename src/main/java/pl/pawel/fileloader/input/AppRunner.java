package pl.pawel.fileloader.input;

import pl.pawel.fileloader.entities.Customer;

import java.util.List;

public interface AppRunner {

    List<Customer> parseData(String fileType, String fileName);

}
