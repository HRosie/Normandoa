package com.example.normand.Controllers.PropertyController;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AgreementController {

    @FXML
    private TextField tenantNameField;
    @FXML
    private TextField propertyField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField monthlyRentField;

    @FXML
    private void handleSaveAgreement() {
        // Logic to save the agreement to the database
        System.out.println("Saving agreement for tenant: " + tenantNameField.getText());
    }

    @FXML
    private void handleDeleteAgreement() {
        // Logic to delete the agreement from the database
        System.out.println("Deleting agreement for tenant: " + tenantNameField.getText());
        clearForm();
    }

    @FXML
    private void handleCancel() {
        // Logic to cancel current operation
        System.out.println("Operation cancelled.");
        clearForm();
    }

    private void clearForm() {
        tenantNameField.clear();
        propertyField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        monthlyRentField.clear();
    }
}

