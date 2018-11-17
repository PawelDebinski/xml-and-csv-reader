package pl.pawel.fileloader.run.impl;

import pl.pawel.fileloader.config.PropertyLoader;
import pl.pawel.fileloader.io.dao.MainDao;
import pl.pawel.fileloader.io.entities.Customer;
import pl.pawel.fileloader.run.AppRunner;
import pl.pawel.fileloader.ui.FileConventer;
import pl.pawel.fileloader.ui.impl.CsvConverter;
import pl.pawel.fileloader.ui.impl.XmlConverter;

import java.util.List;

public class AppRunnerImpl implements AppRunner {

    private MainDao mainDao;
    private FileConventer fileConventer;

    public AppRunnerImpl(MainDao mainDao) {
        this.mainDao = mainDao;
    }

    @Override
    public void runApp() {
        String fileType = PropertyLoader.getFileType();
        String fileName = PropertyLoader.getFileName();

        List<Customer> customers = getCustomers(fileType, fileName);

        mainDao.open();
        for (Customer customer : customers) {
            mainDao.saveCustomer(customer);
        }
        mainDao.close();

        System.out.println("SUCCESS");
    }

    private List<Customer> getCustomers(String fileType, String fileName) {
        List<Customer> customers;
        switch (fileType) {
            case "xml":
                this.fileConventer = new XmlConverter();
                customers = fileConventer.convertFile(fileName);
                break;
            case "csv":
                this.fileConventer = new CsvConverter();
                customers = fileConventer.convertFile(fileName);
                break;
            default:
                customers = null;
        }
        return customers;
    }
}
