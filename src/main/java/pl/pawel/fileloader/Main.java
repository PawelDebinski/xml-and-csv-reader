package pl.pawel.fileloader;

import pl.pawel.fileloader.dao.MainDao;
import pl.pawel.fileloader.dao.impl.MainDaoImpl;
import pl.pawel.fileloader.entities.Customer;
import pl.pawel.fileloader.input.AppRunner;
import pl.pawel.fileloader.input.CsvDataLoader;
import pl.pawel.fileloader.input.XmlDataLoader;
import pl.pawel.fileloader.input.impl.AppRunnerImpl;
import pl.pawel.fileloader.input.impl.CsvDataLoaderImpl;
import pl.pawel.fileloader.input.impl.XmlDataLoaderImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        MainDao mainDao = new MainDaoImpl();
        AppRunner appRunner = new AppRunnerImpl();

        appRunner.loadProperties();
        List<Customer> customers =
                appRunner.parseData(
                        ((AppRunnerImpl) appRunner).getFileType(), ((AppRunnerImpl) appRunner).getFileName());


        for (Customer customer : customers) {
            System.out.println(customer);
        }

        mainDao.open();

        for (Customer customer : customers) {
            mainDao.saveCustomer(customer);
        }

        mainDao.close();


    }


}
