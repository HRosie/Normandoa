package com.example.normand.Controllers.SceneController.Others;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class VisitorDashboardController {
    @FXML
    private TableView<?> propertyTable;

    public void handleViewProperties(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Displaying all properties...");
        alert.showAndWait();
    }

    public void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Logging out...");
        alert.showAndWait();

        // Load the login screen
        ScreenLoader.load("LoginScreen.fxml", (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow());
    }
}
