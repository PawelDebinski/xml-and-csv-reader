package pl.pawel.fileloader.run.impl;

import pl.pawel.fileloader.config.PropertyLoader;
import pl.pawel.fileloader.exceptions.InvalidFileFormatException;
import pl.pawel.fileloader.io.dao.MainDao;
import pl.pawel.fileloader.io.entities.Customer;
import pl.pawel.fileloader.run.AppRunner;
import pl.pawel.fileloader.ui.FileConventer;
import pl.pawel.fileloader.ui.impl.CsvConverter;
import pl.pawel.fileloader.ui.impl.JsonConverter;
import pl.pawel.fileloader.ui.impl.XmlConverter;

import java.util.List;

public class AppRunnerImpl implements AppRunner {

    public AppRunnerImpl() {
    }

    @Override
    public void runApp(MainDao mainDao) {
        String fileType = PropertyLoader.getFileType();
        String fileName = PropertyLoader.getFileName();
        List<Customer> customers = getCustomers(fileType, fileName);
        saveCustomers(mainDao, customers);
        System.out.println("SUCCESS");
    }

    private void saveCustomers(MainDao mainDao, List<Customer> customers) {
        for (Customer customer : customers) {
            mainDao.saveCustomer(customer);
        }
    }

    private List<Customer> getCustomers(String fileType, String fileName) {
        List<Customer> customers;
        FileConventer fileConventer;
        switch (fileType) {
            case "xml":
                fileConventer = new XmlConverter();
                customers = fileConventer.convertFile(fileName);
                break;
            case "csv":
                fileConventer = new CsvConverter();
                customers = fileConventer.convertFile(fileName);
                break;
            case "json":
                fileConventer = new JsonConverter();
                customers = fileConventer.convertFile(fileName);
            default:
                throw new InvalidFileFormatException(fileType);
        }
        return customers;
    }
}
