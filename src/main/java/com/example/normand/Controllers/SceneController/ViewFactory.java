package com.example.normand.Controllers.SceneController;

import com.example.normand.Controllers.PropertyController.AddPropertyController;
import com.example.normand.Controllers.PropertyController.ResidentialController;
import com.example.normand.Controllers.PropertyController.ShowPropertyController;
import com.example.normand.Controllers.SceneController.Owner.OwnerController;
import com.example.normand.Controllers.SceneController.Owner.OwnerHomeController;
import com.example.normand.Models.Owner;
import com.example.normand.Models.Person;
import com.example.normand.Models.Property;
import com.example.normand.Models.Residential;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import SceneController.com.example.normand.Controllers.*;
//import SceneController.Function.*;
import com.example.normand.Database.DatabaseConnection;

import java.io.IOException;
import java.sql.ResultSet;
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


    public void showOwnerWindow(Person model, BorderPane homeScene) {
        try {
            // Initialize FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/normand/Owner/OwnerHome.fxml"));

            // Load the FXML file, which will automatically set the root
            BorderPane root = loader.load();

            // Get the current stage and update the scene
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);  // Replace the current scene root with the loaded root

            // Get the controller and initialize it with the necessary data
            OwnerHomeController controller = loader.getController();
            Owner owner = (Owner) model;
            OwnerController ownerController = new OwnerController(owner, databaseConnection.getConnection());
            controller.initialize(owner, ownerController);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful");
    }

//    public void showOwnerResidential(String id)
//    {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/normand/Form/ResidentialOwnerForm.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.setTitle("Property");
//            stage.setResizable(false);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void showPropertyAddForm(String userId)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/normand/Form/AddPropertyScreen.fxml"));
            Parent root = loader.load();
            AddPropertyController controller = loader.getController();
            controller.setOwnerId(userId);
            controller.postInitialize();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Property");
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOwnerResidential(Residential property) {
        try {
            // Load the FXML file and get the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/normand/Form/ResidentialOwnerForm.fxml"));
            Parent root = loader.load();

            // Get the controller from the loader
            ShowPropertyController controller = loader.getController();

            // Pass the residential property to the controller
            System.out.println(property);
            controller.setResidentalProperty(property);
            // Call postInitialize to set up additional properties
            controller.postInitialize();  // Call this to handle any setup after initialization

            // Set up the stage and show the window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Property");
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
