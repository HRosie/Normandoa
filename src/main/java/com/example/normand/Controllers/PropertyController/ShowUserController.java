package com.example.normand.Controllers.PropertyController;

/**
 * @author sg-random-tut3-group2
 */

import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.Person;
import com.example.normand.Models.Residential;
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

public class ShowUserController {

    @FXML
    private TextField userContact;

    @FXML
    private TextField userDob;

    @FXML
    private Button userExit;

    @FXML
    private TextField userInfo;

    @FXML
    private TextField userName;

    @FXML
    private TextField userRole;

    @FXML
    private Button userUpdate;

    private Person user;

    public void setUser(Person user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        showUserInfo();
    }

    public void postInitialize() {
        userInfo.setDisable(true);

        showUserInfo();
        // If this is necessary to call again after setup
    }

    private void showUserInfo() {
        if (user != null) {
            // Populate fields with the user data
            userInfo.setText(user.getId());
            userName.setText(user.getFullName());
            userDob.setText(String.valueOf(user.getDob()));
            userRole.setText(user.getRole().toString());
            userContact.setText(user.getContactInfo());
        }
    }



    @FXML
    public void UserSaveButton(ActionEvent event){

        String userId = userInfo.getText();
        String userFullName  = userName.getText();
        String userRoles = userRole.getText();
        String userContacts = userContact.getText();
        Date userDate = java.sql.Date.valueOf(userDob.getText());

        //Save update data to the database
        updateProfile(userId, userFullName, userRoles, userContacts, userDate);

        closeCurrentScene(event);

    }

    private void updateProfile(String id, String name, String role, String contact, Date dob)
    {
        try {
            String query = "UPDATE Users SET userid = ?, fullname = ?, role = ?, dob = ?, contact = ? WHERE userid = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, role);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date userDates = formatter.parse(dob.toString());
            java.sql.Date dobs = new java.sql.Date(userDates.getTime());
            statement.setDate(4, dobs);
            statement.setString(5, contact);
            statement.setString(6, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void UserRemoveButton(ActionEvent event) {
        String query = "DELETE FROM users WHERE userId = ?";
        String userId = userInfo.getText();

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, userId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting property.");
        }

        closeCurrentScene(event);
    }

    public void UserExitButton(ActionEvent event) {
        closeCurrentScene(event);
    }


    private void closeCurrentScene(ActionEvent event) {
        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Close the stage (the current window)
    }

}
