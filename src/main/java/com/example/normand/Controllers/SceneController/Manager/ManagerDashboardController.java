package com.example.normand.Controllers.SceneController.Manager;

import com.example.normand.Controllers.SceneController.Others.ScreenLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ManagerDashboardController {
    @FXML
    private TableView<?> propertyTable;

    @FXML
    private TableView<?> tenantTable;

    @FXML
    private TableView<?> hostTable;

    @FXML
    private TableView<?> statisticsTable;

//    public void handleLogout(ActionEvent event) {
//        ScreenLoader.load("LoginScreen.fxml", ((javafx.scene.Node) event.getSource()).getScene().getWindow());
//    }

    public void handleAddProperty(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Adding a new property...");
        alert.showAndWait();
    }

    public void handleUpdateProperty(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Updating selected property...");
        alert.showAndWait();
    }

    public void handleDeleteProperty(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deleting selected property...");
        alert.showAndWait();
    }

    public void handleViewTenants(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Viewing tenants...");
        alert.showAndWait();
    }

    public void handleNotifyTenants(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Notifying tenants...");
        alert.showAndWait();
    }

    public void handleViewHosts(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Viewing hosts...");
        alert.showAndWait();
    }

    public void handleUpdateHost(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Updating host details...");
        alert.showAndWait();
    }

    public void handleGenerateReports(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Generating system-wide reports...");
        alert.showAndWait();
    }

//    public void handleLogout(ActionEvent event) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Logging out...");
//        alert.showAndWait();
//
//        // Load the login screen
//        ScreenLoader.load("LoginScreen.fxml", (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow());
//    }
}
