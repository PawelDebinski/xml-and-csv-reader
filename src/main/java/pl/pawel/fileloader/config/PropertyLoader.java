package pl.pawel.fileloader.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static final String CONFIG_FILE = "main.properties";
    public static final String FILE_TYPE;
    public static final String FILE_NAME;
    public static final String DRIVER;
    public static final String DB_USER;
    public static final String DB_PASSWORD;

    static {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)){
            properties.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FILE_TYPE = properties.getProperty("app.filetype");
        FILE_NAME = properties.getProperty("app.filename");
        DRIVER = properties.getProperty("app.driver");
        DB_USER = properties.getProperty("app.user");
        DB_PASSWORD = properties.getProperty("app.password");
    }

    private PropertyLoader() {
    }

    public static String getFileType() {
        return FILE_TYPE;
    }

    public static String getFileName() {
        return FILE_NAME;
    }

    public static String getDriver() {
        return DRIVER;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }

}
