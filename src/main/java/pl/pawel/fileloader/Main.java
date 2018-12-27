package pl.pawel.fileloader;

import pl.pawel.fileloader.io.dao.impl.MainDaoImpl;
import pl.pawel.fileloader.run.AppRunner;

public class Main {

    public static void main(String[] args) {
        AppRunner.getInstance().runApp();
    }
}
