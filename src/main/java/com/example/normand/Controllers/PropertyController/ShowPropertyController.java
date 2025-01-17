package com.example.normand.Controllers.PropertyController;

import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.Property;
import com.example.normand.Models.Residential;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShowPropertyController {

    @FXML
    private TextField ownerResidentialId;
    @FXML
    private TextField ownerResidentialAddress;
    @FXML
    private TextField ownerResidentialArea;
    @FXML
    private TextField ownerResidentialRooms;
    @FXML
    private TextField ownerResidentialPet;
    @FXML
    private TextField ownerResidentialGarden;

    private Residential property;


    public void setResidentalProperty(Residential property) {
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
        ownerResidentialId.setDisable(true);

        showPropertyInfo();
        // If this is necessary to call again after setup
    }

    private void showPropertyInfo() {
        if (property != null) {
            // Populate fields with the property data
            ownerResidentialId.setText(property.getPropertyId());
            ownerResidentialAddress.setText(property.getPropertyAddress());
            ownerResidentialArea.setText(String.valueOf(property.getPropertySize()));
            ownerResidentialRooms.setText(String.valueOf(property.getRoomAmount()));
            ownerResidentialPet.setText(String.valueOf(property.isPet()));
            ownerResidentialGarden.setText(String.valueOf(property.isGarden()));
        }
    }



    @FXML
    public void ResidentSaveButton(ActionEvent event){

        String propertyId = ownerResidentialId.getText();
        String propertyAddress  = ownerResidentialAddress.getText();
        Double propertyArea = Double.valueOf(ownerResidentialArea.getText());
        Integer propertyRooms = Integer.valueOf(ownerResidentialRooms.getText());
        Boolean propertyPet = Boolean.valueOf(ownerResidentialPet.getText());
        Boolean propertyGarden = Boolean.valueOf(ownerResidentialGarden.getText());

        //Save update data to the database
        updateProfile(propertyId, propertyAddress, propertyArea, propertyRooms, propertyPet, propertyGarden);

        closeCurrentScene(event);

    }

    private void updateProfile(String id, String address, double area, int room, boolean pet, boolean garden)
    {
        try {
            String query = "UPDATE Property SET address = ?, area = ?, room = ?, pet = ?, garden = ? WHERE propertyid = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            System.out.println(query);
            statement.setString(1, address);
            statement.setDouble(2, area);
            statement.setInt(3, room);
            statement.setBoolean(4, pet);
            statement.setBoolean(5, garden);
            statement.setString(6, id);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("rows updated: " + rowsUpdated);
            if (rowsUpdated > 0) {
                System.out.println("An existing property was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ResidentRemoveButton(ActionEvent event) {
        String query = "DELETE FROM property WHERE propertyId = ?";
        String propertyId = ownerResidentialId.getText();

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

    public void ResidentExitButton(ActionEvent event) {
        closeCurrentScene(event);
    }


    private void closeCurrentScene(ActionEvent event) {
        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Close the stage (the current window)
    }

}
