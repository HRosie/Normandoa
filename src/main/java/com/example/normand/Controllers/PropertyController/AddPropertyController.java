package com.example.normand.Controllers.PropertyController;

import com.example.normand.Models.Property;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Controllers.SceneController.Others.Session;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class AddPropertyController {

    @FXML
    private TextField addCommercialAddress;

    @FXML
    private TextField addCommercialArea;

    @FXML
    private Button addCommercialButton;

    @FXML
    private TextField addCommercialId;

    @FXML
    private TextField addCommercialPrice;

    @FXML
    private TabPane addCommercialTab;

    @FXML
    private TextField addCommercialType;

    @FXML
    private TextField addCommericalParking;

    @FXML
    private TextField addResidentAddress;

    @FXML
    private TextField addResidentArea;

    @FXML
    private Button addResidentButton;

    @FXML
    private CheckBox addResidentGarden;

    @FXML
    private TextField addResidentId;

    @FXML
    private CheckBox addResidentPet;

    @FXML
    private TextField addResidentPrice;

    @FXML
    private TextField addResidentRoom;

    @FXML
    private Tab addResidentialTab;
    @FXML
    private TextField addResidentOwner;

    @FXML
    private TextField addCommercialOwner;

    private Stage stage;


    private String ownerId;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @FXML
    public void initialize() {
        addCommercialOwner.setText(ownerId);
        addResidentOwner.setText(ownerId);
    }

    public void postInitialize() {
        addCommercialOwner.setText(ownerId);
        addResidentOwner.setText(ownerId);
    }


    @FXML
    void handleAddCommercial(ActionEvent event) {
        // Collect values from the form
        String propertyId = addCommercialId.getText();
        String propertyAddress = addCommercialAddress.getText();
        String propertyArea = addCommercialArea.getText();
        String propertyPrice = addCommercialPrice.getText();
        String propertyRoom; // Room field (can be empty)
        String parkingValue = addCommericalParking.getText(); // Parking field (can be empty)
        String propertyType = "Commercial"; // You can define this as a fixed type for commercial properties



        // Check if all required fields are filled out
        if (propertyId.isEmpty() || propertyAddress.isEmpty() || propertyArea.isEmpty() || propertyPrice.isEmpty()) {
            addCommercialButton.setDisable(true);
            return;
        }

        // Validate fields
        try {
            // Parse price and area
            double price = Double.parseDouble(propertyPrice);
            double area = Double.parseDouble(propertyArea);

            // Validate that price and area are greater than 0
            if (price < 1 || area < 1) {
                addCommercialButton.setDisable(true);
                return;
            }

            // Handle parking (if provided, parse as double; otherwise, set to null or zero)
            Double parking = null;
            if (!parkingValue.isEmpty()) {
                parking = Double.parseDouble(parkingValue); // Parking is a numeric value
                if (parking < 1) {
                    addCommercialButton.setDisable(true);
                    return;
                }
            }

            // Handle room (if provided, parse as integer; otherwise, set to null)
            Integer rooms = null;

            // Database Insert - Add data to property table
            String query = "INSERT INTO property (propertyId, address, area, price, room, pet, garden, parking, type, ownerId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {

                // Set the values in the prepared statement
                statement.setString(1, propertyId);
                statement.setString(2, propertyAddress);
                statement.setDouble(3, area);  // Use setDouble for area
                statement.setDouble(4, price); // Use setDouble for price

                // Handle the room value: Set NULL if the field is empty

                statement.setNull(5, java.sql.Types.INTEGER); // Set NULL for rooms if not provided


                // Handle pet and garden checkboxes
                statement.setBoolean(6, false);
                statement.setBoolean(7, false);

                // Handle parking value: Set NULL if the field is empty
                statement.setDouble(8, parking); // Otherwise, set the provided parking value

                // Set the property type as "Commercial"
                statement.setString(9, propertyType);
                statement.setString(10, ownerId);

                // Execute the query to insert the data
                statement.executeUpdate();

                // Optionally, give feedback or clear fields
                System.out.println("Commercial property added successfully.");

                if (stage != null) {
                    stage.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: Could not insert data into the property table.");
            }

        } catch (NumberFormatException e) {
            // Handle invalid input (non-numeric values in the numeric fields)
            System.out.println("Invalid input for price, area, rooms, or parking.");
        }
    }


//    void handleAddCommercial(ActionEvent event) {
//        // Collect values from the form
//        boolean petIsSelected = false;
//        boolean gardenIsSelected = false;
//        String propertyId = addCommercialId.getText();
//        String propertyAddress = addCommercialAddress.getText();
//        String propertyArea = addCommercialArea.getText();
//        String propertyPrice = addCommercialPrice.getText();
//        String propertyRoom = null; // Room field may be empty
//        String parkingValue = addCommericalParking.getText();
//        String propertyType = addCommercialType.getText();
//        // Collect parking input (empty if not provided)
//
//        // Check if all required fields are filled out
//        if (propertyId.isEmpty() || propertyAddress.isEmpty() || propertyArea.isEmpty() || propertyPrice.isEmpty()) {
//            addCommercialButton.setDisable(true);
//            return;
//        }
//
//        // Validate fields
//        try {
//            // Parse price, area, and room
//            double price = Double.parseDouble(propertyPrice);
//            double area = Double.parseDouble(propertyArea);
//            double park = Double.parseDouble(parkingValue);
//
//            // If room is provided, parse it, otherwise set to NULL
//            Integer rooms = null;
//            if (!propertyRoom.isEmpty()) {
//                rooms = Integer.parseInt(propertyRoom);
//            }
//
//            // Check if values are valid
//            if (price < 1 || area < 1 || (park < 1)) {
//                addCommercialButton.setDisable(true);
//                return;
//            }
//
//            // Database Insert - Add data to property table
//            String query = "INSERT INTO property (propertyId, address, area, price, room, pet, garden, parking, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//            try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
//
//                // Set the values in the prepared statement
//                statement.setString(1, propertyId);
//                statement.setString(2, propertyAddress);
//                statement.setDouble(3, area);  // Use setDouble for area
//                statement.setDouble(4, price); // Use setDouble for price
//
//                // Handle the room value: Set NULL if the field is empty
//                statement.setNull(5, java.sql.Types.INTEGER); // Set NULL for rooms if not provided
//
//                statement.setBoolean(6, petIsSelected);
//                statement.setBoolean(7, gardenIsSelected);
//
//                // Handle the parking value: Set NULL if the field is empty
//
//                statement.setInt(8, park); // Otherwise, set the provided parking value
//
//                // Set the type value as "Residential"
//                statement.setString(9, propertyType);
//
//                // Execute the query to insert the data
//                statement.executeUpdate();
//
//                // Optionally, give feedback or clear fields
//                System.out.println("Property added successfully.");
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.out.println("Error: Could not insert data into the property table.");
//            }
//
//        } catch (NumberFormatException e) {
//            // Handle invalid input (non-numeric values in the numeric fields)
//            System.out.println("Invalid input for price, area, or rooms.");
//        }
//    }

    @FXML
    void handleAddResidential(ActionEvent event) {
        // Collect values from the form
        boolean petIsSelected = addResidentPet.isSelected(); // Assuming pet checkbox
        boolean gardenIsSelected = addResidentGarden.isSelected(); // Assuming garden checkbox
        String propertyId = addResidentId.getText();
        String propertyAddress = addResidentAddress.getText();
        String propertyArea = addResidentArea.getText();
        String propertyPrice = addResidentPrice.getText();
        String propertyRoom = addResidentRoom.getText(); // Room field will never be empty
        String parkingValue = null; // Parking is always empty



        // Check if all required fields are filled out
        if (propertyId.isEmpty() || propertyAddress.isEmpty() || propertyArea.isEmpty() || propertyPrice.isEmpty() || propertyRoom.isEmpty()) {
            addResidentButton.setDisable(true);
            return;
        }

        // Validate fields
        try {
            // Parse price and area
            double price = Double.parseDouble(propertyPrice);
            double area = Double.parseDouble(propertyArea);

            // Validate price and area: they must be greater than 0
            if (price < 1 || area < 1) {
                addResidentButton.setDisable(true);
                return;
            }

            // Handle room value (it will not be empty, so we can parse it directly)
            Integer rooms = Integer.parseInt(propertyRoom);
            if (rooms < 1) {
                addResidentButton.setDisable(true);
                return;
            }

            // Database Insert - Add data to property table
            String query = "INSERT INTO property (propertyId, address, area, price, room, pet, garden, parking, type, ownerId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {

                // Set the values in the prepared statement
                statement.setString(1, propertyId);
                statement.setString(2, propertyAddress);
                statement.setDouble(3, area);  // Use setDouble for area
                statement.setDouble(4, price); // Use setDouble for price

                // Set the room value directly (rooms will always have a valid value)
                statement.setInt(5, rooms); // Set the room value

                // Handle pet and garden checkboxes
                statement.setBoolean(6, petIsSelected);
                statement.setBoolean(7, gardenIsSelected);

                // Set the parking value to NULL as it will always be empty
                statement.setNull(8, java.sql.Types.BIGINT); // Set NULL for parking as it's empty

                // Set the type value as "Residential"
                statement.setString(9, "Residential");
                statement.setString(10, ownerId);

                // Execute the query to insert the data
                statement.executeUpdate();

                // Optionally, give feedback or clear fields
                System.out.println("Residential property added successfully.");

                Platform.runLater(() -> {
                    Stage stage = (Stage) addResidentButton.getScene().getWindow();
                    stage.close();
                });

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: Could not insert data into the property table.");
            }

        } catch (NumberFormatException e) {
            // Handle invalid input (non-numeric values in the numeric fields)
            System.out.println("Invalid input for price, area, or rooms.");
        }
    }

//    void handleAddResidential(ActionEvent event) {
//        // Collect values from the form
//        boolean petIsSelected = addResidentPet.isSelected();
//        boolean gardenIsSelected = addResidentGarden.isSelected();
//        String propertyId = addResidentId.getText();
//        String propertyAddress = addResidentAddress.getText();
//        String propertyArea = addResidentArea.getText();
//        String propertyPrice = addResidentPrice.getText();
//        String propertyRoom = addResidentRoom.getText(); // Room field may be empty
//        String parkingValue = null; // Collect parking input (empty if not provided)
//
//        // Check if all required fields are filled out
//        if (propertyId.isEmpty() || propertyAddress.isEmpty() || propertyArea.isEmpty() || propertyPrice.isEmpty()) {
//            addResidentButton.setDisable(true);
//            return;
//        }
//
//        // Validate fields
//        try {
//            // Parse price, area, and room
//            double price = Double.parseDouble(propertyPrice);
//            double area = Double.parseDouble(propertyArea);
//
//            // If room is provided, parse it, otherwise set to NULL
//            Integer rooms = null;
//            if (!propertyRoom.isEmpty()) {
//                rooms = Integer.parseInt(propertyRoom);
//            }
//
//            // Check if values are valid
//            if (price < 1 || area < 1 || (rooms != null && rooms < 1)) {
//                addResidentButton.setDisable(true);
//                return;
//            }
//
//            // Database Insert - Add data to property table
//            String query = "INSERT INTO property (propertyId, address, area, price, room, pet, garden, parking, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//            try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
//
//                // Set the values in the prepared statement
//                statement.setString(1, propertyId);
//                statement.setString(2, propertyAddress);
//                statement.setDouble(3, area);  // Use setDouble for area
//                statement.setDouble(4, price); // Use setDouble for price
//
//                // Handle the room value: Set NULL if the field is empty
//                statement.setInt(5, rooms); // Otherwise, set the provided room value
//
//                statement.setBoolean(6, petIsSelected);
//                statement.setBoolean(7, gardenIsSelected);
//
//                // Handle the parking value: Set NULL if the field is empty
//                statement.setNull(8, java.sql.Types.VARCHAR); // Set NULL for parking if not provided
//
//                // Set the type value as "Residential"
//                statement.setString(9, "Residential");
//
//                // Execute the query to insert the data
//                statement.executeUpdate();
//
//                // Optionally, give feedback or clear fields
//                System.out.println("Property added successfully.");
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.out.println("Error: Could not insert data into the property table.");
//            }
//
//        } catch (NumberFormatException e) {
//            // Handle invalid input (non-numeric values in the numeric fields)
//            System.out.println("Invalid input for price, area, or rooms.");
//        }
//    }


    private DatabaseConnection databaseConnection;




    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}


