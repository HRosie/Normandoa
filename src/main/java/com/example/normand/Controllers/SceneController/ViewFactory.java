package com.example.normand.Controllers.SceneController;

/**
 * @author sg-random-tut3-group2
 */

import com.example.normand.Controllers.PropertyController.*;
import com.example.normand.Controllers.SceneController.Host.HostController;
import com.example.normand.Controllers.SceneController.Host.HostHomeController;
import com.example.normand.Controllers.SceneController.Manager.ManagerController;
import com.example.normand.Controllers.SceneController.Manager.ManagerHomeController;
import com.example.normand.Controllers.SceneController.Owner.OwnerController;
import com.example.normand.Controllers.SceneController.Owner.OwnerHomeController;
import com.example.normand.Controllers.SceneController.Tenant.TenantController;
import com.example.normand.Controllers.SceneController.Tenant.TenantHomeController;
import com.example.normand.Controllers.SceneController.Visitor.VisitorController;
import com.example.normand.Controllers.SceneController.Visitor.VisitorHomeController;
import com.example.normand.Models.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import SceneController.com.example.normand.Controllers.*;
//import SceneController.Function.*;
import com.example.normand.Database.DatabaseConnection;

import java.io.IOException;
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

    public void showManagerWindow(Person model, BorderPane homeScene) {
        try {
            // Initialize FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/normand/Manager/ManagerHome.fxml"));

            // Load the FXML file, which will automatically set the root
            BorderPane root = loader.load();


            // Get the current stage and update the scene
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);  // Replace the current scene root with the loaded root

            // Get the controller and initialize it with the necessary data
            ManagerHomeController controller = loader.getController();
            Manager manager = (Manager) model;
            ManagerController managerController = new ManagerController(manager, databaseConnection.getConnection());
            controller.initialize(manager, managerController);
            controller.postInitialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful");
    }

    public void showHostWindow(Person model, BorderPane homeScene) {
        try {
            // Initialize FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/normand/Host/HostHome.fxml"));

            // Load the FXML file, which will automatically set the root
            BorderPane root = loader.load();


            // Get the current stage and update the scene
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);  // Replace the current scene root with the loaded root

            // Get the controller and initialize it with the necessary data
            HostHomeController controller = loader.getController();
            Host host = (Host) model;
            HostController hostController = new HostController(host, databaseConnection.getConnection());
            controller.initialize(host, hostController);
            controller.postInitialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful");
    }

    public void showTenantWindow(Person model, BorderPane homeScene) {
        try {
            // Initialize FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/normand/Tenant/TenantHome.fxml"));

            // Load the FXML file, which will automatically set the root
            BorderPane root = loader.load();


            // Get the current stage and update the scene
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);  // Replace the current scene root with the loaded root

            // Get the controller and initialize it with the necessary data
            TenantHomeController controller = loader.getController();
            Tenant tenant = (Tenant) model;
            TenantController tenantController = new TenantController(tenant, databaseConnection.getConnection());
            controller.initialize(tenant, tenantController);
            controller.postInitialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Login Successful");
    }

    public void showVisitorWindow(BorderPane homeScene) {
        try {
            // Initialize FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/normand/Visitor/VisitorHome.fxml"));

            // Load the FXML file, which will automatically set the root
            BorderPane root = loader.load();


            // Get the current stage and update the scene
            Stage stage = (Stage) homeScene.getScene().getWindow();
            stage.getScene().setRoot(root);  // Replace the current scene root with the loaded root

            // Get the controller and initialize it with the necessary data
            VisitorHomeController controller = loader.getController();
            VisitorController visitorController = new VisitorController(databaseConnection.getConnection());
            controller.initialize(visitorController);
            controller.postInitialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Visitor Login Successful");
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

    public void showAdminPropertyAddForm()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/normand/Form/AddPropertyScreen.fxml"));
            Parent root = loader.load();
            AddPropertyController controller = loader.getController();
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

    public void showOwnerCommercial(Commercial property) {
        try {
            // Load the FXML file and get the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/normand/Form/CommercialOwnerForm.fxml"));
            Parent root = loader.load();

            // Get the controller from the loader
            ShowCommercialController controller = loader.getController();

            // Pass the residential property to the controller
            controller.setCommercialProperty(property);
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


    public void showUserAddForm()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/normand/Form/AddUserScreen.fxml"));
            Parent root = loader.load();
            AddUserController controller = loader.getController();
            controller.postInitialize();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("User");
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUserViewForm(Person user) {
        try {
            // Load the FXML file and get the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/normand/Form/ShowUser.fxml"));
            Parent root = loader.load();

            // Get the controller from the loader
            ShowUserController controller = loader.getController();

            // Pass the residential property to the controller
            controller.setUser(user);
            // Call postInitialize to set up additional properties
            controller.postInitialize();  // Call this to handle any setup after initialization

            // Set up the stage and show the window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("User");
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRentalAddForm()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/normand/Form/AddAgreementScreen.fxml"));
            Parent root = loader.load();
            AddRentalController controller = loader.getController();
            controller.postInitialize();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Rental Agrreement");
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void showRentalViewForm(RentalAgreement rentalAgreement) {
        try {
            // Load the FXML file and get the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/normand/Form/ShowRental.fxml"));
            Parent root = loader.load();

            // Get the controller from the loader
            ShowRentalController controller = loader.getController();

            // Pass the residential property to the controller
            controller.setRental(rentalAgreement);
            // Call postInitialize to set up additional properties
            controller.postInitialize();  // Call this to handle any setup after initialization

            // Set up the stage and show the window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("User");
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
