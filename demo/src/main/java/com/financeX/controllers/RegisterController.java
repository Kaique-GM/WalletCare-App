package com.financeX.controllers;

import java.io.IOException;

import com.financeX.model.entities.User;
import com.financeX.services.MonthService;
import com.financeX.services.Session;
import com.financeX.services.UserService;
import com.financeX.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    UserService service = new UserService();
    MonthService serviceM = new MonthService();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private void OnRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            Alerts.showAlert("Error", null, "Fields cannot be empty", AlertType.ERROR);
            return;
        }

        try {
            Boolean userExists = service.userExists(username);

            if (userExists) {
                Alerts.showAlert("Error", null, "User already exists", AlertType.ERROR);
                return;
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            Session session = Session.getInstance();
            session.setUsername(username);
            session.setUserID(service.findIdByUsername(username));

            service.saveOrUpdate(user);

            service.insert12Months(user);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Scene homeScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) registerButton.getScene().getWindow();

            stage.close();

            Stage homeStage = new Stage();
            homeStage.setScene(homeScene);

            homeStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the home screen", AlertType.ERROR);
        }
    }
}
