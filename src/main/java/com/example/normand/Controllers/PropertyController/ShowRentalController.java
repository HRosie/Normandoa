package com.example.normand.Controllers.PropertyController;

/**
 * @author sg-random-tut3-group2
 */

import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.Person;
import com.example.normand.Models.RentalAgreement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowRentalController {

    @FXML
    private TextField viewRentalEnd;

    @FXML
    private Button viewRentalExit;

    @FXML
    private TextField viewRentalFee;

    @FXML
    private TextField viewRentalHost;

    @FXML
    private TextField viewRentalId;

    @FXML
    private TextField viewRentalOwner;

    @FXML
    private TextField viewRentalProperty;

    @FXML
    private Button viewRentalRemove;

    @FXML
    private TextField viewRentalStart;

    @FXML
    private TextField viewRentalStatus;

    @FXML
    private TextField viewRentalTenant;

    @FXML
    private Button viewRentalUpdate;

    private RentalAgreement rental;

    public void setRental(RentalAgreement rentalAgreement) {
        this.rental = rentalAgreement;
    }

    @FXML
    public void initialize() {
        showRentalInfo();
    }

    public void postInitialize() {
        viewRentalId.setDisable(true);

        showRentalInfo();
        // If this is necessary to call again after setup
    }

    private void showRentalInfo() {
        if (rental != null) {
            // Populate fields with the user data
            viewRentalId.setText(rental.getRentalId());
            viewRentalHost.setText(rental.getHostId());
            viewRentalOwner.setText(rental.getOwnerId());
            viewRentalTenant.setText(rental.getTenantId());
            viewRentalProperty.setText(rental.getPropertyId());
            viewRentalFee.setText(String.valueOf(rental.getFee()));
            viewRentalStatus.setText(rental.getStatus());
            viewRentalStart.setText(String.valueOf(rental.getStartDate()));
            viewRentalEnd.setText(String.valueOf(rental.getEndDate()));

        }
    }

    @FXML
    public void RentalSaveButton(ActionEvent event){

        String rentalId = viewRentalId.getText();
        String propertyId = viewRentalProperty.getText();
        String ownerId = viewRentalOwner.getText();
        String hostId = viewRentalHost.getText();
        String tenantId = viewRentalTenant.getText();
        Double rentalFee  = Double.valueOf(viewRentalFee.getText());
        String rentalStatus = viewRentalStatus.getText();
        Date startDate = java.sql.Date.valueOf(viewRentalStart.getText());
        Date endDate = java.sql.Date.valueOf(viewRentalEnd.getText());


        //Save update data to the database
        updateProfile(rentalId, propertyId, ownerId, hostId, tenantId, rentalFee, rentalStatus, startDate,endDate);

        closeCurrentScene(event);

    }

    private void updateProfile(String id, String property, String owner, String host, String tenant, double fee, String status,  Date start, Date end)
    {
        try {
            String query = "UPDATE Agreement SET rentalid = ?, ownerid = ?, hostid = ?, tenantid = ?, propertyid = ?, fee = ?, status = ?, startdate = ?, enddate = ? WHERE rentalid = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, owner);
            statement.setString(3, host);
            statement.setString(4, tenant);
            statement.setString(5, property);
            statement.setDouble(6, fee);
            statement.setString(7, status);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date userDates = formatter.parse(start.toString());
            java.sql.Date dobs = new java.sql.Date(userDates.getTime());
            statement.setDate(8, dobs);
            java.util.Date userDatess = formatter.parse(end.toString());
            java.sql.Date dobss = new java.sql.Date(userDatess.getTime());
            statement.setDate(9, dobss);
            statement.setString(10, id);


            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing Agreement was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void RentalRemoveButton(ActionEvent event) {
        String query = "DELETE FROM agreement WHERE rentalId = ?";
        String rentalId = viewRentalId.getText();

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, rentalId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Agreement deleted successfully.");
            } else {
                System.out.println("Agreement not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting agreement.");
        }

        closeCurrentScene(event);
    }

    public void RentalExitButton(ActionEvent event) {
        closeCurrentScene(event);
    }


    private void closeCurrentScene(ActionEvent event) {
        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Close the stage (the current window)
    }


}
