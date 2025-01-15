package com.example.normand.Controllers.SceneController.Form;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddPropertyController {

    @FXML
    private TextField propertyNameField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField imagePathField;

    private Stage stage;  // Assume stage is injected or initialized elsewhere

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            imagePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void handleAddProperty() {
        // Logic to add property to the database
        System.out.println("Adding property: " + propertyNameField.getText());
    }

    @FXML
    private void handleDeleteProperty() {
        // Logic to delete property from the database
        System.out.println("Deleting property: " + propertyNameField.getText());
        clearForm();
    }

    @FXML
    private void handleCancel() {
        // Logic to cancel current operation
        System.out.println("Operation cancelled.");
        clearForm();
    }

    private void clearForm() {
        propertyNameField.clear();
        locationField.clear();
        priceField.clear();
        imagePathField.clear();
    }
}
