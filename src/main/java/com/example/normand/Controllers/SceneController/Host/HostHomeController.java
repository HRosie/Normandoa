package com.example.normand.Controllers.SceneController.Host;

/**
 * @author sg-random-tut3-group2
 */

import com.example.normand.Controllers.SceneController.Others.SceneUtil;
import com.example.normand.Controllers.SceneController.Host.HostController;
import com.example.normand.Controllers.SceneController.ViewFactory;
import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class HostHomeController {

    @FXML
    private Button hostAddAgreement;

    @FXML
    private ComboBox hostAgreementFilter;

    @FXML
    private TextField hostAgreementSearchBox;

    @FXML
    private TextField hostContact;

    @FXML
    private TextField hostDob;

    @FXML
    private Button hostEditInfo;

    @FXML
    private TableColumn hostEnd;

    @FXML
    private TextField hostFullName;

    @FXML
    private TableColumn hostHostId;

    @FXML
    private Tab hostInfoTab;

    @FXML
    private Button hostLogout;

    @FXML
    private TextField hostPassword;

    @FXML
    private TableColumn hostPropertyAddress;

    @FXML
    private TableColumn hostPropertyId;

    @FXML
    private TableColumn hostPropertyPrice;

    @FXML
    private TextField hostPropertySearchBox;

    @FXML
    private Button hostPropertySearchButton;

    @FXML
    private Button hostPropertySearchButton1;

    @FXML
    private Tab hostPropertyTab;

    @FXML
    private TableColumn hostPropertyType;

    @FXML
    private TableColumn hostRentalId;

    @FXML
    private TableColumn hostRentalStatus;

    @FXML
    private Tab hostRentalTab;

    @FXML
    private Button hostSaveInfo;

    @FXML
    private TableColumn hostStart;

    @FXML
    private TableColumn hostTenantId;

    @FXML
    private TextField hostUserId;

    @FXML
    private TextField hostUsername;

    @FXML
    private Button hostViewAgreement;

    @FXML
    private Button hostViewProperty;

    @FXML
    private TableView hostPropertyTable;

    @FXML
    private TableView hostRentalTable;

    @FXML
    private Region spacer;

    @FXML
    private BorderPane hostHome;

    private RentalAgreement selectedRental = null;
    private Property selectedProperty = null;


    private Host host;
    private HostController hostController;
    DatabaseConnection databaseConnection;

    private String id;
    private String fullName;
    private String contact;
    private Date dob;
    private String username;
    private String password;

    private ViewFactory view;

    @FXML
    void handleGenerateReports(ActionEvent event) {

    }

    @FXML
    void handleHostLogout(ActionEvent event) throws IOException {
        SceneUtil.logout(hostHome);
    }

    @FXML
    void showHostProperty(ActionEvent event) {
        if(selectedProperty != null){
            hostPropertyTable.getSelectionModel().clearSelection();
            hostViewProperty.setDisable(false);
        } if(selectedProperty.getPropertyType().equals("Residential")){
            view.showOwnerResidential(getResidentialPropertyById(selectedProperty.getPropertyId()));
        } else {
            view.showOwnerCommercial(getCommercialPropertyById(selectedProperty.getPropertyId()));
        }
    }
    public HostHomeController() {
        this.databaseConnection = databaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    public void initialize(Host host, HostController hostController) {
        this.host = host;
        this.hostController = hostController;
        populateUserPropertyTable();
        hostInfoTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // This means the tab is selected
                handleProfileTabSelection(); // Call the method to handle the tab selection
            }
        });

        hostPropertyTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateUserPropertyTable();
                hostViewProperty.setDisable(true);
            }
        });

        hostRentalTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateHostRentalTable();
            }
        });

        hostPropertyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedProperty = (Property) newSelection;
                hostViewProperty.setDisable(false);
            } else {
                hostViewProperty.setDisable(true);
            }
        });

        hostRentalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedRental = (RentalAgreement) newSelection;
                hostViewAgreement.setDisable(false);
            } else {
                hostViewAgreement.setDisable(true);
            }
        });
    }

    public void postInitialize() {

        populateUserPropertyTable();
    }

    @FXML
    public void handleProfileTabSelection() {
        if (hostInfoTab.isSelected()) {
            String[] information = hostController.getInfo().split("\n");
            hostUserId.setText(information[0].split(": ")[1]);
            hostFullName.setText(information[1].split(": ")[1]);
            hostUsername.setText(information[2].split(": ")[1]);
            hostPassword.setText(information[3].split(": ")[1]);
            hostDob.setText(information[4].split(": ")[1]);
            hostContact.setText(information[5].split(": ")[1]);
        }

    }


    private void populateUserPropertyTable(){
        List<Property> datas = hostController.getProperty();
        ObservableList<Property> data = FXCollections.observableArrayList(datas);
        hostPropertyId.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        hostPropertyAddress.setCellValueFactory(new PropertyValueFactory<>("propertyAddress"));
        hostPropertyPrice.setCellValueFactory(new PropertyValueFactory<>("propertyPrice"));
        hostPropertyType.setCellValueFactory(new PropertyValueFactory<>("propertyType"));

        FilteredList<Property> filteredData = new FilteredList<>(data, b -> true);

        hostPropertySearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
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

        hostPropertyTable.setItems(filteredData);

        if (selectedProperty != null){
            hostPropertyTable.getSelectionModel().select(selectedProperty);
        }

    }

    private void populateHostRentalTable(){
        List<RentalAgreement> datas = hostController.getRentalAgreement();
        ObservableList<RentalAgreement> data = FXCollections.observableArrayList(datas);

        hostRentalId.setCellValueFactory(new PropertyValueFactory<>("rentalId"));
        hostTenantId.setCellValueFactory(new PropertyValueFactory<>("tenantId"));
        hostHostId.setCellValueFactory(new PropertyValueFactory<>("hostId"));
        hostRentalStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        hostStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        hostEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        FilteredList<RentalAgreement> filteredData = new FilteredList<>(data, b -> true);

        hostAgreementSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(rentalAgreement -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (rentalAgreement.getRentalId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (rentalAgreement.getHostId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (rentalAgreement.getTenantId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (rentalAgreement.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        hostRentalTable.setItems(filteredData);
        if (selectedRental != null){
            hostRentalTable.getSelectionModel().select(selectedRental);
        }

    }




    @FXML
    public void onProfileSaveButton(ActionEvent event){

        String password = hostPassword.getText();
        String contact = hostContact.getText();
        String fullName = hostFullName.getText();
        String username = hostUsername.getText();

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
//    public void setHostAddProperty() {
//        ViewFactory view = new ViewFactory(databaseConnection);
//        view.showPropertyAddForm();
//    }


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


    public Commercial getCommercialPropertyById(String propertyId) {
        Commercial property = null;
        String query = "SELECT * FROM property WHERE propertyId = ? AND type <> 'Residential'";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) { // Corrected this line
            statement.setString(1, propertyId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Create a Property object based on the result set
                property = new Commercial(null,null,0,0,null,0,null);
                property.setPropertyId(resultSet.getString("propertyId"));
                property.setPropertyAddress(resultSet.getString("address"));
                property.setPropertySize(resultSet.getDouble("area"));
                property.setPropertyPrice(resultSet.getDouble("price"));
                property.setParkingSpace(resultSet.getInt("parking"));
                property.setPropertyType(resultSet.getString("type"));
                property.setOwnerId(resultSet.getString("ownerId"));
                property.setPropertyPrice(resultSet.getDouble("price"));
                // Add any other fields here as needed
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return property;
    }

    @FXML
    public void onHostAddRental(ActionEvent event){

        ViewFactory view = new ViewFactory(databaseConnection);
        view.showRentalAddForm();

    }

    @FXML
    public void onHostViewRental(ActionEvent event){

        ViewFactory view = new ViewFactory(databaseConnection);
        view.showRentalViewForm(selectedRental);

    }


}
