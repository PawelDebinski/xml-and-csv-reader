package pl.pawel.fileloader.ui;

import pl.pawel.fileloader.io.entities.Customer;

import java.util.List;

public interface FileConventer {

    List<Customer> convertFile(String fileName);

}
