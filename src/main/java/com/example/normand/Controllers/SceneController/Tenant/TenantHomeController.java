package com.example.normand.Controllers.SceneController.Tenant;

import com.example.normand.Controllers.SceneController.Others.SceneUtil;
import com.example.normand.Controllers.SceneController.ViewFactory;
import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.Model;
import com.example.normand.Models.Property;
import com.example.normand.Models.Tenant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TenantHomeController {

    @FXML
    private TextField tenantContact;

    @FXML
    private TextField tenantDob;

    @FXML
    private TextField tenantFullName;

    @FXML
    private TableColumn tenantPropertyName;

    @FXML
    private TableColumn tenantPropertyPrice;

    @FXML
    private TextField tenantPropertySearchBox;

    @FXML
    private Button tenantPropertySearchButton;

    @FXML
    private TableView tenantPropertyTable;

    @FXML
    private TableColumn tenantPropertyType;

    @FXML
    private Button tenantLogout;

    @FXML
    private BorderPane tenantHome;

    private Tenant tenant;
    private DatabaseConnection databaseConnection;

    private String id;
    private String fullName;
    private String contact;
    private Date dob;

    private ViewFactory view;
    private Property selectedProperty = null;

    public TenantHomeController() {
        this.databaseConnection = DatabaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    @FXML
    void handleTenantLogout(ActionEvent event) throws IOException {
        SceneUtil.logout(tenantHome);
    }

    @FXML
    void showTenantProperty(ActionEvent event) {
        if (selectedProperty != null) {
            tenantPropertyTable.getSelectionModel().clearSelection();
        }
    }

    public void initialize(Tenant tenant) {
        this.tenant = tenant;
        populateTenantPropertyTable();
    }

    private void populateTenantPropertyTable() {
        List<Property> properties = tenant.getProperties();
        ObservableList<Property> data = FXCollections.observableArrayList(properties);

        tenantPropertyName.setCellValueFactory(new PropertyValueFactory<>("propertyName"));
        tenantPropertyPrice.setCellValueFactory(new PropertyValueFactory<>("propertyPrice"));
        tenantPropertyType.setCellValueFactory(new PropertyValueFactory<>("propertyType"));

        FilteredList<Property> filteredData = new FilteredList<>(data, b -> true);

        tenantPropertySearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(property -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return property.getPropertyName().toLowerCase().contains(lowerCaseFilter)
                        || property.getPropertyType().toLowerCase().contains(lowerCaseFilter);
            });
        });

        tenantPropertyTable.setItems(filteredData);
    }
}
