package com.example.normand.Controllers.PropertyController;

import com.example.normand.Database.DatabaseConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AddRentalController {

    @FXML
    private DatePicker newEnd;

    @FXML
    private TextField newFee;

    @FXML
    private TextField newHostId;

    @FXML
    private TextField newOwnerId;

    @FXML
    private TextField newPropertyId;

    @FXML
    private Button newRentalButton;

    @FXML
    private TextField newRentalId;

    @FXML
    private DatePicker newStart;

    @FXML
    private TextField newStatus;

    @FXML
    private TextField newTenantId;

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
    void handleAddRental(ActionEvent event) {
        // Collect values from the form
        String rentalId = newRentalId.getText();
        String hostId = newHostId.getText();
        String tenantId = newTenantId.getText();
        String ownerId = newOwnerId.getText();
        String propertyId = newPropertyId.getText();
        Date startDate = java.sql.Date.valueOf(newStart.getValue().toString());
        Date endDate = java.sql.Date.valueOf(newEnd.getValue().toString());
        Double fee = Double.valueOf(newFee.getText());
        String status = newStatus.getText();




        // Check if all required fields are filled out
        if (tenantId.isEmpty() || hostId.isEmpty() || rentalId.isEmpty() || ownerId.isEmpty() || propertyId.isEmpty() || status.isEmpty()) {
            newRentalButton.setDisable(true);
            return;
        }

        try {


            // Database Insert - Add data to property table
            String query = "INSERT INTO Agreement (hostId, tenantId, ownerId, propertyId, fee, status, startdate, enddate, rentalid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {

                // Set the values in the prepared statement
                statement.setString(1, hostId);
                statement.setString(2, tenantId);
                statement.setString(3, ownerId);  // Use setDouble for area
                statement.setString(4, propertyId); // Use setDouble for price

                // Handle the room value: Set NULL if the field is empty

                statement.setDouble(5, fee);
                statement.setString(6, status);// Set NULL for rooms if not provided


                // Handle pet and garden checkboxes
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date userDate = formatter.parse(startDate.toString());
                java.sql.Date date = new java.sql.Date(userDate.getTime());
                statement.setDate(7, date);
                java.util.Date edDate = formatter.parse(endDate.toString());
                java.sql.Date edate = new java.sql.Date(edDate.getTime());
                statement.setDate(8, edate);
                statement.setString(9, rentalId);

                // Execute the query to insert the data
                statement.executeUpdate();

                // Optionally, give feedback or clear fields
                System.out.println("Rental Agreement added successfully.");


                Platform.runLater(() -> {
                    Stage stage = (Stage) newRentalButton.getScene().getWindow();
                    stage.close();
                });

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: Could not insert data into the property table.");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        } catch (NumberFormatException e) {
        }
    }

    private DatabaseConnection databaseConnection;




    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


}
