package com.example.normand.Controllers.SceneController.Tenant;

import com.example.normand.Controllers.SceneController.Tenant.TenantController;
import com.example.normand.Controllers.SceneController.Others.SceneUtil;
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

public class TenantHomeController {

    @FXML
    private Region spacer;

    @FXML
    private ComboBox tenantAgreementFilter;

    @FXML
    private TextField tenantAgreementSearchBox;

    @FXML
    private TextField tenantContact;

    @FXML
    private TextField tenantDob;

    @FXML
    private TableColumn tenantEnd;

    @FXML
    private TextField tenantFullName;

    @FXML
    private TableColumn tenantTenantId;

    @FXML
    private Tab tenantInfoTab;

    @FXML
    private Button tenantLogout;

    @FXML
    private TextField tenantPassword;

    @FXML
    private TableColumn tenantPropertyAddress;

    @FXML
    private TableColumn tenantPropertyId;

    @FXML
    private TableColumn tenantPropertyPrice;

    @FXML
    private TextField tenantPropertySearchBox;

    @FXML
    private Button tenantPropertySearchButton;

    @FXML
    private Button tenantPropertySearchButton1;

    @FXML
    private Tab tenantPropertyTab;

    @FXML
    private TableView tenantPropertyTable;

    @FXML
    private TableColumn tenantPropertyType;

    @FXML
    private TableColumn tenantRentalId;

    @FXML
    private TableColumn tenantRentalStatus;

    @FXML
    private Tab tenantRentalTab;

    @FXML
    private TableView tenantRentalTable;

    @FXML
    private Button tenantSaveInfo;

    @FXML
    private TableColumn tenantStart;

    @FXML
    private TextField tenantUserId;

    @FXML
    private TextField tenantUsername;

    @FXML
    private Button tenantViewAgreement;

    @FXML
    private Button tenantViewProperty;

    @FXML
    private BorderPane tenantHome;

    @FXML
    private TableColumn tenantHostId;

    private RentalAgreement selectedRental = null;
    private Property selectedProperty = null;


    private Tenant tenant;
    private TenantController tenantController;
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
    void handleTenantLogout(ActionEvent event) throws IOException {
        SceneUtil.logout(tenantHome);
    }

    @FXML
    void showTenantProperty(ActionEvent event) {
        if(selectedProperty != null){
            tenantPropertyTable.getSelectionModel().clearSelection();
            tenantViewProperty.setDisable(false);
        } if(selectedProperty.getPropertyType().equals("Residential")){
            view.showOwnerResidential(getResidentialPropertyById(selectedProperty.getPropertyId()));
        } else {
            System.out.println(getCommercialPropertyById(selectedProperty.getPropertyId()));
            view.showOwnerCommercial(getCommercialPropertyById(selectedProperty.getPropertyId()));
        }
    }
    public TenantHomeController() {
        this.databaseConnection = databaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    public void initialize(Tenant tenant, TenantController tenantController) {
        this.tenant = tenant;
        this.tenantController = tenantController;
        populateUserPropertyTable();
        tenantInfoTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // This means the tab is selected
                handleProfileTabSelection(); // Call the method to handle the tab selection
            }
        });

        tenantPropertyTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateUserPropertyTable();
                tenantViewProperty.setDisable(true);
            }
        });

        tenantRentalTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateTenantRentalTable();
            }
        });

        tenantPropertyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedProperty = (Property) newSelection;
                tenantViewProperty.setDisable(false);
            } else {
                tenantViewProperty.setDisable(true);
            }
        });

        tenantRentalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedRental = (RentalAgreement) newSelection;
                tenantViewAgreement.setDisable(false);
            } else {
                tenantViewAgreement.setDisable(true);
            }
        });
    }

    public void postInitialize() {

        populateUserPropertyTable();
    }

    @FXML
    public void handleProfileTabSelection() {
        if (tenantInfoTab.isSelected()) {
            String[] information = tenantController.getInfo().split("\n");
            tenantUserId.setText(information[0].split(": ")[1]);
            tenantFullName.setText(information[1].split(": ")[1]);
            tenantUsername.setText(information[2].split(": ")[1]);
            tenantPassword.setText(information[3].split(": ")[1]);
            tenantDob.setText(information[4].split(": ")[1]);
            tenantContact.setText(information[5].split(": ")[1]);
        }

    }


    private void populateUserPropertyTable(){
        List<Property> datas = tenantController.getProperty();
        ObservableList<Property> data = FXCollections.observableArrayList(datas);
        tenantPropertyId.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        tenantPropertyAddress.setCellValueFactory(new PropertyValueFactory<>("propertyAddress"));
        tenantPropertyPrice.setCellValueFactory(new PropertyValueFactory<>("propertyPrice"));
        tenantPropertyType.setCellValueFactory(new PropertyValueFactory<>("propertyType"));

        FilteredList<Property> filteredData = new FilteredList<>(data, b -> true);

        tenantPropertySearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
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

        tenantPropertyTable.setItems(filteredData);

        if (selectedProperty != null){
            tenantPropertyTable.getSelectionModel().select(selectedProperty);
            System.out.println(selectedProperty);
        } else {
            System.out.println("TestSelect");
        }

    }

    private void populateTenantRentalTable(){
        List<RentalAgreement> datas = tenantController.getRentalAgreement();
        ObservableList<RentalAgreement> data = FXCollections.observableArrayList(datas);

        tenantRentalId.setCellValueFactory(new PropertyValueFactory<>("rentalId"));
        tenantHostId.setCellValueFactory(new PropertyValueFactory<>("tenantId"));
        tenantRentalStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tenantStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tenantEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        FilteredList<RentalAgreement> filteredData = new FilteredList<>(data, b -> true);

        tenantAgreementSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
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

        tenantRentalTable.setItems(filteredData);
        if (selectedRental != null){
            tenantRentalTable.getSelectionModel().select(selectedRental);
            System.out.println(selectedRental);
        } else {
            System.out.println("TestSelect");
        }

    }




    @FXML
    public void onProfileSaveButton(ActionEvent event){

        String password = tenantPassword.getText();
        String contact = tenantContact.getText();
        String fullName = tenantFullName.getText();
        String username = tenantUsername.getText();

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
    public void onTenantAddRental(ActionEvent event){

        ViewFactory view = new ViewFactory(databaseConnection);
        view.showRentalAddForm();

    }

    @FXML
    public void onTenantViewRental(ActionEvent event){

        ViewFactory view = new ViewFactory(databaseConnection);
        view.showRentalViewForm(selectedRental);

    }


}
