package com.example.normand.Controllers.SceneController.Form;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AddUserController {
    @FXML
    private TextField userId;
    @FXML
    private TextField fullName;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextField contactInfo;
    @FXML
    private ComboBox<String> userRole;

    @FXML
    private void handleAddUser() {
        // Retrieve values from the form
        String id = userId.getText();
        String name = fullName.getText();
        LocalDate dob = dateOfBirth.getValue();
        String contact = contactInfo.getText();
        String role = userRole.getSelectionModel().getSelectedItem();

        // Validate the input
        if (!validateInput(id, name, dob, contact, role)) {
            System.out.println("Validation failed.");
            // Ideally show an alert or a message on the UI here
            return;
        }

        // Process the user addition, e.g., adding to a database
        addUserToDatabase(id, name, dob, contact, role);
        System.out.println("User added successfully!");
    }

    // Validate input data
    private boolean validateInput(String id, String name, LocalDate dob, String contact, String role) {
        if (id == null || id.trim().isEmpty() || name.trim().isEmpty() || dob == null || contact.trim().isEmpty() || role == null) {
            return false; // validation failed
        }
        return true; // validation passed
    }

    // Method to add user data to a database or some storage
    private void addUserToDatabase(String id, String name, LocalDate dob, String contact, String role) {
        // Implement database interaction here
        // This is a placeholder for your database operation
    }
}
