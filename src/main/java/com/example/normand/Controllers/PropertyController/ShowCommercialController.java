package com.example.normand.Controllers.PropertyController;

import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.Commercial;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShowCommercialController {

    @FXML
    private TextField ownerCommercialId;
    @FXML
    private TextField ownerCommercialAddress;
    @FXML
    private TextField ownerCommercialArea;
    @FXML
    private TextField ownerCommercialType;
    @FXML
    private TextField ownerCommercialParking;
    @FXML
    private TextField ownerCommercialPrice;

    private Commercial property;


    public void setCommercialProperty(Commercial property) {
        this.property = property;
    }

    // initialize() is automatically called when the FXML is loaded
    @FXML
    public void initialize() {
        if (property != null) {
            showPropertyInfo();
        } else {
            System.out.println("property is null");
        }
    }

    public void postInitialize() {
        // Additional setup after initialization, if needed
        ownerCommercialId.setDisable(true);

        showPropertyInfo();
        // If this is necessary to call again after setup
    }

    private void showPropertyInfo() {
        if (property != null) {
            // Populate fields with the property data
            ownerCommercialId.setText(property.getPropertyId());
            ownerCommercialAddress.setText(property.getPropertyAddress());
            ownerCommercialArea.setText(String.valueOf(property.getPropertySize()));
            ownerCommercialType.setText(property.getPropertyType());
            ownerCommercialParking.setText(String.valueOf(property.getPropertySize()));
            ownerCommercialPrice.setText(String.valueOf(property.getPropertyPrice()));
        }
    }



    @FXML
    public void CommercialSaveButton(ActionEvent event){

        String propertyId = ownerCommercialId.getText();
        String propertyAddress  = ownerCommercialAddress.getText();
        Double propertyArea = Double.valueOf(ownerCommercialArea.getText());
        String propertyType = ownerCommercialType.getText();
        Double propertyParking = Double.valueOf(ownerCommercialParking.getText());
        Double propertyPrice = Double.valueOf(ownerCommercialPrice.getText());

        //Save update data to the database
        updateProfile(propertyId, propertyAddress, propertyArea, propertyType, propertyParking, propertyPrice);

        closeCurrentScene(event);

    }

    private void updateProfile(String id, String address, double area, String type, Double park, double price)
    {
        try {
            String query = "UPDATE Property SET address = ?, area = ?, type = ?, parking = ?, price = ? WHERE propertyid = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            System.out.println(query);
            statement.setString(1, address);
            statement.setDouble(2, area);
            statement.setString(3, type);
            statement.setDouble(4, park );
            statement.setDouble(5, price);
            statement.setString(6, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing property was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void CommercialRemoveButton(ActionEvent event) {
        String query = "DELETE FROM property WHERE propertyId = ?";
        String propertyId = ownerCommercialId.getText();

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, propertyId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Property deleted successfully.");
            } else {
                System.out.println("Property not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting property.");
        }

        closeCurrentScene(event);
    }

    public void CommercialExitButton(ActionEvent event) {
        closeCurrentScene(event);
    }


    private void closeCurrentScene(ActionEvent event) {
        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Close the stage (the current window)
    }

}
