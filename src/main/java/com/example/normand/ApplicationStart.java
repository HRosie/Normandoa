package com.example.normand;

/**
 * @author sg-random-tut3-group2
 */

import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationStart extends Application {
    private DatabaseConnection databaseConnection;

    @Override
    public void init() {
        databaseConnection = DatabaseConnection.getInstance();
        databaseConnection.getConnection();
    }
    @Override
    public void start(Stage stage) throws IOException {
        Model.getInstance().getView().showLoginScene();
    }


    @Override
    public void stop() {
        databaseConnection.disconnect();
    }

}