package pl.pawel.fileloader;

import pl.pawel.fileloader.dao.MainDao;
import pl.pawel.fileloader.dao.impl.MainDaoImpl;
import pl.pawel.fileloader.entities.Customer;
import pl.pawel.fileloader.input.AppRunner;
import pl.pawel.fileloader.input.impl.PropertyLoader;
import pl.pawel.fileloader.input.impl.AppRunnerImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        PropertyLoader.loadProperties();
        MainDao mainDao = new MainDaoImpl();
        AppRunner appRunner = new AppRunnerImpl();


        List<Customer> customers =
                appRunner.parseData(PropertyLoader.getFileType(), PropertyLoader.getFileName());

        mainDao.open();
        for (Customer customer : customers) {
            mainDao.saveCustomer(customer);
        }
        mainDao.close();

        System.out.println("SUCCESS");


    }

}
