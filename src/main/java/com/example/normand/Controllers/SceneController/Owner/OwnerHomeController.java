package com.example.normand.Controllers.SceneController.Owner;

import com.example.normand.Controllers.SceneController.Others.SceneUtil;
import com.example.normand.Controllers.SceneController.ViewFactory;
import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class OwnerHomeController {

    @FXML
    private Button ownerAddProperty;

    @FXML
    private TextField ownerContact;

    @FXML
    private TextField ownerDob;

    @FXML
    private Button ownerEditInfo;

    @FXML
    private TextField ownerFullName;

    @FXML
    private TableColumn ownerHostName;

    @FXML
    private TableColumn ownerHostProperty;


    @FXML
    private TextField ownerHostSearchBox;

    @FXML
    private Button ownerHostSearchButton;

    @FXML
    private Tab ownerHostTab;

    @FXML
    private TableView ownerHostTable;

    @FXML
    private Tab ownerInfoTab;

    @FXML
    private Button ownerLogout;

    @FXML
    private TableColumn ownerPropertyAddress;

    @FXML
    private TableColumn ownerPropertyPrice;

    @FXML
    private TextField ownerPropertySearchBox;

    @FXML
    private Button ownerPropertySearchButton;

    @FXML
    private Tab ownerPropertyTab;

    @FXML
    private TableView ownerPropertyTable;

    @FXML
    private TableColumn ownerPropertyType;

    @FXML
    private Button ownerSaveInfo;

    @FXML
    private TextField ownerUserId;

    @FXML
    private TextField ownerUsername;

    @FXML
    private TextField ownerPassword;

    @FXML
    private Button ownerViewProperty;

    @FXML
    private Region spacer;

    @FXML
    private BorderPane ownerHome;
    @FXML
    private TableColumn ownerPropertyId;


    private Owner owner;
    private OwnerController ownerController;
    DatabaseConnection databaseConnection;

    private String id;
    private String fullName;
    private String contact;
    private Date dob;
    private String username;
    private String password;

    private ViewFactory view;
    private Property selectedProperty = null;

    @FXML
    void handleGenerateReports(ActionEvent event) {

    }

    @FXML
    void handleOwnerLogout(ActionEvent event) throws IOException {
        SceneUtil.logout(ownerHome);
    }

    @FXML
    void showOwnerProperty(ActionEvent event) {
        if(selectedProperty != null){
            ownerPropertyTable.getSelectionModel().clearSelection();
            ownerViewProperty.setDisable(false);
        } if(selectedProperty.getPropertyType().equals("Residential")){
            System.out.println(selectedProperty);
            System.out.println(getResidentialPropertyById(selectedProperty.getPropertyId()));
            view.showOwnerResidential(getResidentialPropertyById(selectedProperty.getPropertyId()));
        } else {
            //view.showOwnerCommercial(selectedProperty.getPropertyId());
        }
    }
    public OwnerHomeController() {
        this.databaseConnection = databaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    public void initialize(Owner owner, OwnerController ownerController) {
        this.owner = owner;
        this.ownerController = ownerController;
        populateUserPropertyTable();
        if (ownerInfoTab.isSelected()) {
            handleProfileTabSelection();
        }

        ownerPropertyTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateUserPropertyTable();
                ownerAddProperty.setDisable(false);
                ownerViewProperty.setDisable(true);
            }
        });

        ownerHostTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateUserHostTable();
            }
        });

        ownerPropertyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedProperty = (Property) newSelection;
                ownerViewProperty.setDisable(false);
            } else {
                ownerViewProperty.setDisable(true);
            }
        });
    }

    @FXML
    public void handleProfileTabSelection() {
        if (ownerInfoTab.isSelected()) {
            String[] information = ownerController.getInfo().split("\n");
            System.out.println(information[2]);
            ownerUserId.setText(information[0].split(": ")[1]);
            ownerFullName.setText(information[1].split(": ")[1]);
            ownerUsername.setText(information[2].split(": ")[1]);
            ownerPassword.setText(information[3].split(": ")[1]);
            ownerDob.setText(information[4].split(": ")[1]);
            ownerContact.setText(information[5].split(": ")[1]);
        }

    }

//    private void populateUserPropertyTable() {
//        List<Property> properties = ownerController.getProperty();
//        ObservableList<Property> data = FXCollections.observableArrayList(properties);
//        System.out.println(properties);
//        ownerPropertyAddress.setCellValueFactory(new PropertyValueFactory<>("propertyAddress"));
//        ownerPropertyPrice.setCellValueFactory(new PropertyValueFactory<>("propertyPrice"));
//        ownerPropertyType.setCellValueFactory(new PropertyValueFactory<>("propertyType"));
//
//        ownerPropertyTable.setItems(data);
//        if (selectedProperty != null)
//        {
//            ownerPropertyTable.getSelectionModel().select(selectedProperty);
//        }
//    }

    private void populateUserPropertyTable(){
        List<Property> datas = ownerController.getProperty();
        ObservableList<Property> data = FXCollections.observableArrayList(datas);
        ownerPropertyId.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        ownerPropertyAddress.setCellValueFactory(new PropertyValueFactory<>("propertyAddress"));
        ownerPropertyPrice.setCellValueFactory(new PropertyValueFactory<>("propertyPrice"));
        ownerPropertyType.setCellValueFactory(new PropertyValueFactory<>("propertyType"));

        FilteredList<Property> filteredData = new FilteredList<>(data, b -> true);

        ownerPropertySearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(property -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (property.getPropertyId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (property.getPropertyAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        ownerPropertyTable.setItems(filteredData);

        if (selectedProperty != null){
            ownerPropertyTable.getSelectionModel().select(selectedProperty);
            System.out.println(selectedProperty);
        } else {
            System.out.println("TestSelect");
        }

    }

    private void populateUserHostTable(){
        List<RentalAgreement> datas = ownerController.getRentalAgreement();
        ObservableList<RentalAgreement> data = FXCollections.observableArrayList(datas);

        ownerHostProperty.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        ownerHostName.setCellValueFactory(new PropertyValueFactory<>("hostId"));

        FilteredList<RentalAgreement> filteredData = new FilteredList<>(data, b -> true);

        ownerHostSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(rentalAgreement -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (rentalAgreement.getPropertyId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (rentalAgreement.getHostId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        ownerHostTable.setItems(filteredData);

    }


    @FXML
    public void onProfileSaveButton(ActionEvent event){

        String password = ownerPassword.getText();
        String contact = ownerContact.getText();
        String fullName = ownerFullName.getText();
        String username = ownerUsername.getText();

        //Save update data to the database
        updateProfile(password, contact, fullName, username);

    }

    private void updateProfile(String password, String contact, String fullName, String username)
    {
        try {
            String query = "UPDATE Users SET password = ?, contact = ?, fullname = ? WHERE username = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);

            statement.setString(1, password);
            statement.setString(2, contact);
            statement.setString(3, fullName);
            statement.setString(4, username);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    public void setOwnerAddProperty() {
//        ViewFactory view = new ViewFactory(databaseConnection);
//        view.showPropertyAddForm();
//    }

    @FXML
    public void onOwnerAddProperty(ActionEvent event){

        ViewFactory view = new ViewFactory(databaseConnection);
        view.showPropertyAddForm(owner.getId());

    }

    public Residential getResidentialPropertyById(String propertyId) {
        Residential property = null;
        String query = "SELECT * FROM property WHERE propertyId = ? AND type = 'Residential'";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) { // Corrected this line
            statement.setString(1, propertyId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Create a Property object based on the result set
                property = new Residential(null,null,0,0,null,null,0,true,false);
                property.setPropertyId(resultSet.getString("propertyId"));
                property.setPropertyAddress(resultSet.getString("address"));
                property.setPropertySize(resultSet.getDouble("area"));
                property.setPropertyPrice(resultSet.getDouble("price"));
                property.setRoomAmount(resultSet.getInt("room"));
                property.setPropertyType(resultSet.getString("type"));
                property.setOwnerId(resultSet.getString("ownerId"));
                property.setPet(resultSet.getBoolean("pet"));
                property.setGarden(resultSet.getBoolean("garden"));
                // Add any other fields here as needed
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return property;
    }
}
