package com.example.normand.Controllers.SceneController.Others;

/**
 * @author sg-random-tut3-group2
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenLoader {

    public static void load(String fxmlFile, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenLoader.class.getResource(fxmlFile));
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
