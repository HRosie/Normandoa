package com.example.normand.Controllers.SceneController.Others;

/**
 * @author sg-random-tut3-group2
 */

import com.example.normand.Controllers.SceneController.ViewFactory;
import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.Model;
import com.example.normand.Models.Person;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.example.normand.Models.Role;

public class LoginSceneController {
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginResult;

    @FXML
            private BorderPane homeScene;


    DatabaseConnection databaseConnection= DatabaseConnection.getInstance();
    ViewFactory view = new ViewFactory(databaseConnection);
    Stage stage;

    @FXML
    public void login(ActionEvent event) {
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        Person user = databaseConnection.getUser(username, password);

        Session.getInstance().setUser(user);

        Model model = Model.getInstance();
        ViewFactory view = new ViewFactory(databaseConnection);


        new Thread(() -> {
            model.evaluateUserCred(username, password);

            if (user == null || password.isEmpty()){
                Platform.runLater(() -> loginResult.setText("Invalid username or password. Please try again!"));
            } else
            if (model.getLoginSuccess()) {
                Role role = model.getLoginRole();
                switch (role) {
                    case Manager:
                        view.showManagerWindow(user, homeScene);
                        break;

                    case Owner:
                        view.showOwnerWindow(user, homeScene);
                        break;

                    case Host:
                        view.showHostWindow(user, homeScene);
                        break;

                    case Tenant:
                        view.showTenantWindow(user, homeScene);
                        break;

                    default:
                        break;
                }
            } else {
                Platform.runLater(() -> loginResult.setText("Invalid username or password"));
            }
        }).start();
    }

    public void VisitorLogin(ActionEvent event) {
        view.showVisitorWindow(homeScene);
    }

}
