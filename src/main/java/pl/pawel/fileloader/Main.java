package pl.pawel.fileloader;

import pl.pawel.fileloader.io.dao.impl.MainDaoImpl;
import pl.pawel.fileloader.run.AppRunner;
import pl.pawel.fileloader.run.impl.AppRunnerImpl;

public class Main {

    public static void main(String[] args) {

        AppRunner appRunner = new AppRunnerImpl(new MainDaoImpl());
        appRunner.runApp();



    }

}
