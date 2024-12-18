package com.financeX.controllers;

import java.io.IOException;

import com.financeX.services.Session;
import com.financeX.services.UserService;
import com.financeX.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private UserService service = new UserService();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            Alerts.showAlert("Error", null, "Fields cannot be empty", AlertType.ERROR);
            return;
        }

        try {

            Boolean userExists = service.userExists(username);
            Boolean passwordMatches = service.passwordMatches(username, password);

            if (userExists && passwordMatches) {
                Session session = Session.getInstance();
                session.setUsername(username);
                session.setUserID(service.findIdByUsername(username));

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
                Scene homeScene = new Scene(fxmlLoader.load());

                Stage stage = (Stage) loginButton.getScene().getWindow();

                stage.close();

                Stage homeStage = new Stage();
                homeStage.setScene(homeScene);
                homeStage.show();
            }

            else if (!userExists) {
                Alerts.showAlert("Error", null, "User not found", AlertType.ERROR);
                return;
            } else if (!passwordMatches) {
                Alerts.showAlert("Error", null, "Incorrect password", AlertType.ERROR);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error", AlertType.ERROR);

        }
    }

    @FXML
    private void OnRegister(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/register.fxml"));
            Scene registerScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) registerButton.getScene().getWindow();

            stage.close();

            Stage registerStage = new Stage();
            registerStage.setScene(registerScene);
            registerStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "An error occurred", AlertType.ERROR);
        }

    }
}