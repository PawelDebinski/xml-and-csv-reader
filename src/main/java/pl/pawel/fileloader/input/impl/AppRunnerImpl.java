package pl.pawel.fileloader.input.impl;

import pl.pawel.fileloader.entities.Customer;
import pl.pawel.fileloader.input.AppRunner;
import pl.pawel.fileloader.input.CsvDataLoader;
import pl.pawel.fileloader.input.XmlDataLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AppRunnerImpl implements AppRunner {

    CsvDataLoader csvDataLoader;
    XmlDataLoader xmlDataLoader;

    public CsvDataLoader getCsvDataLoader() {
        return csvDataLoader;
    }

    public void setCsvDataLoader(CsvDataLoader csvDataLoader) {
        this.csvDataLoader = csvDataLoader;
    }

    public XmlDataLoader getXmlDataLoader() {
        return xmlDataLoader;
    }

    public void setXmlDataLoader(XmlDataLoader xmlDataLoader) {
        this.xmlDataLoader = xmlDataLoader;
    }



    @Override
    public List<Customer> parseData(String fileType, String fileName) {
        List<Customer> customers;
        switch (fileType) {
            case "xml":
                this.xmlDataLoader = new XmlDataLoaderImpl();
                customers = xmlDataLoader.returnCustomerList(fileName);
                break;
            case "csv":
                this.csvDataLoader = new CsvDataLoaderImpl();
                customers = csvDataLoader.loadCsv(fileName);
                break;
            default:
                customers = null;
        }
        return customers;
    }
}
