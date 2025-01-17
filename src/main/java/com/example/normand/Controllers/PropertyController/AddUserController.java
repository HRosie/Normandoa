package com.example.normand.Controllers.PropertyController;

import com.example.normand.Database.DatabaseConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.util.Date;
import java.time.LocalDate;

public class AddUserController {


        @FXML
        private Button addUserButton;

        @FXML
        private TextField addUserContact;

        @FXML
        private DatePicker addUserDob;

        @FXML
        private TextField addUserFullName;

        @FXML
        private TextField addUserId;

        @FXML
        private TextField addUserRole;

    private Stage stage;



    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
    }

    public void postInitialize() {
    }


    @FXML
    void handleAddUser(ActionEvent event) {
        // Collect values from the form
        String userId = addUserId.getText();
        String userFullName = addUserFullName.getText();
        LocalDate localDate = addUserDob.getValue();
        Date userDob = java.sql.Date.valueOf(localDate);
        String userContact = addUserContact.getText();
        String userRole = addUserRole.getText(); // Room field (can be empty)




        // Check if all required fields are filled out
        if (userId.isEmpty() || userFullName.isEmpty() || userContact.isEmpty() || userRole.isEmpty()) {
            addUserButton.setDisable(true);
            return;
        }

        // Validate fields
        try {

            String username = null;
            String password = null;

            // Database Insert - Add data to property table
            String query = "INSERT INTO Users (userId, fullName, username, password, role, dob, contact) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {

                // Set the values in the prepared statement
                statement.setString(1, userId);
                statement.setString(2, userFullName);
                statement.setString(3, username);  // Use setDouble for area
                statement.setString(4, password); // Use setDouble for price

                // Handle the room value: Set NULL if the field is empty

                statement.setString(5, userRole); // Set NULL for rooms if not provided


                // Handle pet and garden checkboxes
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date userDate = formatter.parse(userDob.toString());
                java.sql.Date dob = new java.sql.Date(userDate.getTime());
                statement.setDate(6, dob);
                statement.setString(7, userContact);

                // Execute the query to insert the data
                statement.executeUpdate();

                // Optionally, give feedback or clear fields
                System.out.println("Commercial property added successfully.");


                Platform.runLater(() -> {
                    Stage stage = (Stage) addUserButton.getScene().getWindow();
                    stage.close();
                });

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: Could not insert data into the property table.");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        } catch (NumberFormatException e) {
            // Handle invalid input (non-numeric values in the numeric fields)
            System.out.println("Invalid input for price, area, rooms, or parking.");
        }
    }

    private DatabaseConnection databaseConnection;




    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}


