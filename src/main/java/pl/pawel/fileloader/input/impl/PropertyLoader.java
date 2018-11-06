package pl.pawel.fileloader.input.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    public static String fileType;
    public static String fileName;
    public static String driver;
    public static String dbUser;
    public static String dbPassword;

    public static String getFileType() {
        return fileType;
    }

    public static void setFileType(String fileType) {
        PropertyLoader.fileType = fileType;
    }

    public static String getFileName() {
        return fileName;
    }

    public static void setFileName(String fileName) {
        PropertyLoader.fileName = fileName;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        PropertyLoader.driver = driver;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static void setDbUser(String dbUser) {
        PropertyLoader.dbUser = dbUser;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static void setDbPassword(String dbPassword) {
        PropertyLoader.dbPassword = dbPassword;
    }

    public static void loadProperties() {
     Properties properties = new Properties();
        try (InputStream input = new FileInputStream("./main.properties")) {
            properties.load(input);
            fileType = properties.getProperty("app.filetype");
            fileName = properties.getProperty("app.filename");
            driver = properties.getProperty("app.driver");
            dbUser = properties.getProperty("app.user");
            dbPassword = properties.getProperty("app.password");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
