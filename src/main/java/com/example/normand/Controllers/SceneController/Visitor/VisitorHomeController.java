package com.example.normand.Controllers.SceneController.Visitor;

/**
 * @author sg-random-tut3-group2
 */

import com.example.normand.Controllers.SceneController.Visitor.VisitorController;
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
import java.util.List;

public class VisitorHomeController {

    @FXML
    private TableView visitorPropertyTable;

    @FXML
    private Region spacer;

    @FXML
    private Button visitorLogout;

    @FXML
    private TableColumn visitorPropertyAddress;

    @FXML
    private TableColumn visitorPropertyId;

    @FXML
    private TableColumn visitorPropertyPrice;

    @FXML
    private TextField visitorPropertySearchBox;

    @FXML
    private Button visitorPropertySearchButton;

    @FXML
    private Tab visitorPropertyTab;

    @FXML
    private TableColumn visitorPropertyType;

    @FXML
    private Button visitorViewProperty;

    @FXML
    private BorderPane visitorHome;


    private VisitorController visitorController;
    DatabaseConnection databaseConnection;


    private ViewFactory view;
    private Property selectedProperty = null;



    @FXML
    void handleVisitorLogout(ActionEvent event) throws IOException {
        SceneUtil.logout(visitorHome);
    }

    @FXML
    void showVisitorProperty(ActionEvent event) {
        if(selectedProperty != null){
            visitorPropertyTable.getSelectionModel().clearSelection();
            visitorViewProperty.setDisable(false);
        } if(selectedProperty.getPropertyType().equals("Residential")){
            view.showOwnerResidential(getResidentialPropertyById(selectedProperty.getPropertyId()));
        } else {
            view.showOwnerCommercial(getCommercialPropertyById(selectedProperty.getPropertyId()));
        }
    }
    public VisitorHomeController() {
        this.databaseConnection = databaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    public void initialize(VisitorController controller) {
        this.visitorController = controller;

        visitorPropertyTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateUserPropertyTable();
                visitorViewProperty.setDisable(true);
            }
        });


        visitorPropertyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedProperty = (Property) newSelection;
                visitorViewProperty.setDisable(false);
            } else {
                visitorViewProperty.setDisable(true);
            }
        });
    }


    public void postInitialize() {
        populateUserPropertyTable();
    }


    private void populateUserPropertyTable(){
        List<Property> datas = visitorController.getProperty();
        ObservableList<Property> data = FXCollections.observableArrayList(datas);
        visitorPropertyId.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        visitorPropertyAddress.setCellValueFactory(new PropertyValueFactory<>("propertyAddress"));
        visitorPropertyPrice.setCellValueFactory(new PropertyValueFactory<>("propertyPrice"));
        visitorPropertyType.setCellValueFactory(new PropertyValueFactory<>("propertyType"));

        FilteredList<Property> filteredData = new FilteredList<>(data, b -> true);

        visitorPropertySearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
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

        visitorPropertyTable.setItems(filteredData);

        if (selectedProperty != null){
            visitorPropertyTable.getSelectionModel().select(selectedProperty);
        }

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


}
