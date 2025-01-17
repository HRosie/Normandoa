package com.example.normand.Controllers.SceneController.Manager;

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

public class ManagerHomeController {

    @FXML
    private Button adminAddAgreement;

    @FXML
    private Button adminAddProperty;

    @FXML
    private Button adminAddUser;

    @FXML
    private ComboBox<?> adminAgreementFilter;

    @FXML
    private TextField adminAgreementSearchBox;

    @FXML
    private TableColumn adminEnd;

    @FXML
    private TableColumn adminHostId;

    @FXML
    private Button adminLogout;

    @FXML
    private TableColumn adminPropertyAddress;

    @FXML
    private TableColumn adminPropertyPrice;

    @FXML
    private TextField adminPropertySearchBox;

    @FXML
    private Button adminPropertySearchButton;

    @FXML
    private Tab adminPropertyTab;

    @FXML
    private TableColumn adminPropertyType;

    @FXML
    private TableColumn adminRentalId;

    @FXML
    private TableColumn adminRentalStatus;

    @FXML
    private Tab adminRentalTab;

    @FXML
    private TextField adminSearchUserBox;

    @FXML
    private Button adminSearchUserButton;

    @FXML
    private TableColumn adminStart;

    @FXML
    private TableColumn adminTenantId;

    @FXML
    private ComboBox adminUserFilter;

    @FXML
    private TableColumn adminUserId;

    @FXML
    private TableColumn adminUserName;

    @FXML
    private TableColumn adminUserPassword;

    @FXML
    private TableColumn adminUserRole;

    @FXML
    private Tab adminUserTab;

    @FXML
    private TableColumn adminUserUsername;

    @FXML
    private Button adminViewAgreement;

    @FXML
    private Button adminViewProperty;

    @FXML
    private Button adminViewUser;

    @FXML
    private TableView managerPropertyTable;

    @FXML
    private TableView adminRentalTable;

    @FXML
    private TableView adminUserTable;

    @FXML
    private Region spacer;

    @FXML
    private TableColumn adminPropertyId;

    @FXML
    private BorderPane managerHome;


    private Manager manager;
    private ManagerController managerController;
    DatabaseConnection databaseConnection;


    private ViewFactory view;
    private Property selectedProperty = null;
    private Person selectedUser = null;
    private RentalAgreement selectedRental = null;

    @FXML
    void handleGenerateReports(ActionEvent event) {

    }

    @FXML
    void handleManagerLogout(ActionEvent event) throws IOException {
        SceneUtil.logout(managerHome);
    }

    @FXML
    void showManagerProperty(ActionEvent event) {
        if(selectedProperty != null){
            managerPropertyTable.getSelectionModel().clearSelection();
            adminViewProperty.setDisable(false);
        } if(selectedProperty.getPropertyType().equals("Residential")){
            view.showOwnerResidential(getResidentialPropertyById(selectedProperty.getPropertyId()));
        } else {
            System.out.println(getCommercialPropertyById(selectedProperty.getPropertyId()));
            view.showOwnerCommercial(getCommercialPropertyById(selectedProperty.getPropertyId()));
        }
    }
    public ManagerHomeController() {
        this.databaseConnection = databaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    public void initialize(Manager manager, ManagerController controller) {
        this.manager = manager;
        this.managerController = controller;

        adminPropertyTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateUserPropertyTable();
                adminAddProperty.setDisable(false);
                adminViewProperty.setDisable(true);
            }
        });

        adminUserTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateUserTable();
                adminAddUser.setDisable(false);
                adminViewUser.setDisable(true);
            }
        });

        adminRentalTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateRentalTable();
                adminAddAgreement.setDisable(false);
                adminViewAgreement.setDisable(true);
            }
        });

        managerPropertyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedProperty = (Property) newSelection;
                adminViewProperty.setDisable(false);
            } else {
                adminViewProperty.setDisable(true);
            }
        });

        adminUserTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedUser = (Person) newSelection;
                adminViewUser.setDisable(false);
            } else {
                adminViewUser.setDisable(true);
            }
        });

        adminRentalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedRental = (RentalAgreement) newSelection;
                adminViewAgreement.setDisable(false);;
            } else {
                adminViewAgreement.setDisable(true);
            }
        });
    }

    public void postInitialize() {
        populateUserPropertyTable();
    }


    private void populateUserPropertyTable(){
        List<Property> datas = managerController.getProperty();
        ObservableList<Property> data = FXCollections.observableArrayList(datas);
        adminPropertyId.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        adminPropertyAddress.setCellValueFactory(new PropertyValueFactory<>("propertyAddress"));
        adminPropertyPrice.setCellValueFactory(new PropertyValueFactory<>("propertyPrice"));
        adminPropertyType.setCellValueFactory(new PropertyValueFactory<>("propertyType"));

        FilteredList<Property> filteredData = new FilteredList<>(data, b -> true);

        adminPropertySearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
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

        managerPropertyTable.setItems(filteredData);

        if (selectedProperty != null){
            managerPropertyTable.getSelectionModel().select(selectedProperty);
            System.out.println(selectedProperty);
        } else {
            System.out.println("TestSelect");
        }

    }

    private void populateUserTable(){
        List<Person> datas = managerController.getUser();
        ObservableList<Person> data = FXCollections.observableArrayList(datas);

        adminUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        adminUserName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        adminUserUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        adminUserPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        adminUserRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        FilteredList<Person> filteredData = new FilteredList<>(data, b -> true);

        adminSearchUserBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getFullName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (person.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        adminUserTable.setItems(filteredData);
        if (selectedUser != null){
            adminUserTable.getSelectionModel().select(selectedUser);
            System.out.println(selectedUser);
        } else {
            System.out.println("TestSelect");
        }

    }

    private void populateRentalTable(){
        List<RentalAgreement> datas = managerController.getRentalAgreement();
        ObservableList<RentalAgreement> data = FXCollections.observableArrayList(datas);

        adminRentalId.setCellValueFactory(new PropertyValueFactory<>("rentalId"));
        adminTenantId.setCellValueFactory(new PropertyValueFactory<>("tenantId"));
        adminHostId.setCellValueFactory(new PropertyValueFactory<>("hostId"));
        adminRentalStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        adminStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        adminEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        FilteredList<RentalAgreement> filteredData = new FilteredList<>(data, b -> true);

        adminAgreementSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
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

        adminRentalTable.setItems(filteredData);
        if (selectedRental != null){
            adminRentalTable.getSelectionModel().select(selectedRental);
            System.out.println(selectedRental);
        } else {
            System.out.println("TestSelect");
        }

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


    @FXML
    public void onManagerAddProperty(ActionEvent event){

        ViewFactory view = new ViewFactory(databaseConnection);
        view.showAdminPropertyAddForm();

    }

    @FXML
    public void onManagerAddRental(ActionEvent event){

        ViewFactory view = new ViewFactory(databaseConnection);
        view.showRentalAddForm();

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
    public void onManagerAddUser(ActionEvent event){

        ViewFactory view = new ViewFactory(databaseConnection);
        view.showUserAddForm();

    }

    @FXML
    public void onManagerViewUser(ActionEvent event){

        ViewFactory view = new ViewFactory(databaseConnection);
        view.showUserViewForm(selectedUser);

    }
}
