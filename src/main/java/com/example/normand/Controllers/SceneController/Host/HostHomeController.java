package com.example.normand.Controllers.SceneController.Host;

import com.example.normand.Controllers.SceneController.Others.SceneUtil;
import com.example.normand.Controllers.SceneController.ViewFactory;
import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.Host;
import com.example.normand.Models.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;

public class HostHomeController {

    @FXML
    private BorderPane hostHome;

    @FXML
    private TableView<Property> hostPropertyTable;

    @FXML
    private TableColumn<Property, String> propertyNameColumn;

    @FXML
    private TableColumn<Property, String> propertyTypeColumn;

    @FXML
    private TableColumn<Property, String> propertyStatusColumn;

    @FXML
    private TextField searchPropertyField;

    @FXML
    private Button logoutButton;

    private Host host;
    private DatabaseConnection databaseConnection;

    public HostHomeController() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneUtil.logout(hostHome);
    }

    public void initialize() {
        populateProperties();
    }

    private void populateProperties() {
        List<Property> properties = host.getProperties();
        ObservableList<Property> propertyData = FXCollections.observableArrayList(properties);
        propertyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        propertyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        propertyStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        FilteredList<Property> filteredData = new FilteredList<>(propertyData, p -> true);
        searchPropertyField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(property -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newVal.toLowerCase();
                return property.getName().toLowerCase().contains(lowerCaseFilter)
                        || property.getType().toLowerCase().contains(lowerCaseFilter)
                        || property.getStatus().toLowerCase().contains(lowerCaseFilter);
            });
        });
        hostPropertyTable.setItems(filteredData);
    }
}
