package pl.pawel.fileloader.run;

import pl.pawel.fileloader.config.PropertyLoader;
import pl.pawel.fileloader.exceptions.InvalidFileFormatException;
import pl.pawel.fileloader.io.dao.MainDao;
import pl.pawel.fileloader.io.dao.impl.MainDaoImpl;
import pl.pawel.fileloader.io.entities.Customer;
import pl.pawel.fileloader.ui.FileConventer;
import pl.pawel.fileloader.ui.impl.CsvConverter;
import pl.pawel.fileloader.ui.impl.XmlConverter;

import java.util.List;

public class AppRunner {
    private static AppRunner instance = null;

    private AppRunner() {
    }

    public static AppRunner getInstance() {
        if (instance  == null) {
            instance = new AppRunner();
        }
        return instance;
    }

    public void runApp() {
        String fileType = PropertyLoader.getFileType();
        String fileName = PropertyLoader.getFileName();
        List<Customer> customers = getCustomers(fileType, fileName);
        saveCustomers(customers);
        System.out.println("SUCCESS");
    }

    private void saveCustomers(List<Customer> customers) {
        MainDao mainDao = MainDaoImpl.getInstance();
        for (Customer customer : customers) {
            mainDao.saveCustomer(customer);
        }
        mainDao.close();
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
            default:
                throw new InvalidFileFormatException(fileType);
        }
        return customers;
    }
}
