package com.example.normand.Controllers.SceneController.Others;

/**
 * @author sg-random-tut3-group2
 */

import com.example.normand.Models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.example.normand.Database.DatabaseConnection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class SceneUtil {
    private static Pane currentScene;

    public static void itemFilter(ObservableList<Person> items, TableView<Person> tableView) {
        FilteredList<Person> filteredItems = new FilteredList<>(items, b -> true);

        SortedList<Person> sortedItems = new SortedList<>(filteredItems);
        sortedItems.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedItems);
    }

    public static void setCurrentScene(BorderPane currentScene) {
        SceneUtil.currentScene = currentScene;
    }

    public static void logout(BorderPane pane) throws IOException {
        Optional<ButtonType> confirmation = confirmationDialog(
                "Logout",
                "Are you sure you want to logout?",
                "You will be redirected to the home page"
        );

        if (confirmation.get() == ButtonType.OK) {
            setCurrentScene(pane);
            changeScene(pane, "/com/example/normand/LoginScreen.fxml");
        }
    }

    private static void changeScene(BorderPane currentScene, String fxmlPath) throws IOException {
        BorderPane nextScene = FXMLLoader.load(Objects.requireNonNull(SceneUtil.class.getResource(fxmlPath)));
        changeCleanScene(currentScene, nextScene);
    }


    private static void changeCleanScene(BorderPane currentScene, BorderPane nextScene) {
        currentScene.getChildren().removeAll();
        currentScene.getChildren().setAll(nextScene);
    }


    private static Optional<ButtonType> confirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

//    public static ObservableList<Person> getDependentItemList() {
//        ObservableList<Person> dependentList = getAllChildPolicyOwner();
//        return dependentList.filtered(user -> user.getRole().equals("Dependent"));
//    }

//    public static ObservableList<User> getPHItemList() {
//        ObservableList<User> phList = getAllChildPolicyOwner();
//        return phList.filtered(user -> user.getRole().equals("Policy Holder"));
//    }

//    private static ObservableList<User> getAllChildPolicyOwner() {
//        ObservableList<User> allUsers = FXCollections.observableArrayList();
//        try {
//            Connection connection = DatabaseConnection.getInstance().getConnection();
//            String query = "SELECT * FROM Users u " +
//                    "JOIN policyholder p ON u.id = p.dependentid OR u.id = p.policyholderid " +
//                    "WHERE p.policyholderid = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            OwnerHomeController ownerHomeController = new OwnerHomeController();
//            ownerHomeController.populatePolicyOwnerTable();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return allUsers;
//    }
}
