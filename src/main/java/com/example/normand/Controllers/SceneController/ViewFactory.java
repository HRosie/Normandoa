package com.example.normand.Controllers.SceneController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
//import SceneController.com.example.normand.Controllers.*;
//import SceneController.Function.*;
import com.example.normand.Database.DatabaseConnection;
//import SceneController.Manager.ManagerHomeController;


public class ViewFactory {
    private DatabaseConnection databaseConnection;

    public ViewFactory(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void showLoginScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/normand/LoginScreen.fxml"));
//        Scene scene = null;
        Parent scene = null;
        try {
//            scene = new Scene(loader.load());
            scene = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Normand");
        stage.setResizable(false);
        //stage.getIcons().add(new Image(getClass().getResource("com/example/normand2/Images/AppIcon.png").toExternalForm()));
        stage.setScene(new Scene(scene));
        stage.show();
    }


//    public void showManagerWindow(User model, AnchorPane homeScene) {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/ui/gp/Scene/Manager/Manager.fxml"));
//            AnchorPane root = new AnchorPane();
//            loader.setRoot(root);
//            root = loader.load();
//            Stage stage = (Stage) homeScene.getScene().getWindow();
//            stage.getScene().setRoot(root);
//            ManagerHomeController controller = loader.getController();
//            Manager manager = (Manager) model;
//            ManagerController managerController = new ManagerController(manager, databaseConnection.getConnection());
//            controller.initialize(manager, managerController);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Login Successful");
//    }



}
