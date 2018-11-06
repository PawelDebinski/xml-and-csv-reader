package pl.pawel.fileloader.input;

import pl.pawel.fileloader.entities.Contact;
import pl.pawel.fileloader.entities.Customer;

import java.util.List;

public interface CsvDataLoader {

    List<Customer> loadCsv(String fileName);

}
