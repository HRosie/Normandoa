package com.example.normand.Controllers.SceneController.Owner;

import com.example.normand.Controllers.SceneController.Others.SceneUtil;
import com.example.normand.Controllers.SceneController.ViewFactory;
import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.Host;
import com.example.normand.Models.Model;
import com.example.normand.Models.Owner;
import com.example.normand.Models.Property;
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
import java.util.Date;
import java.util.List;

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
    private TableColumn ownerHostRental;

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
            view.showOwnerResidential(selectedProperty.getPropertyId());
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
        List<Host> datas = ownerController.getHost();
        ObservableList<Property> data = FXCollections.observableArrayList(datas);

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
}
