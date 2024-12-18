package com.financeX.controllers;

import java.io.IOException;

import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class HomeController {
    
    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem logOutButton;

    @FXML
    private void onLogout(ActionEvent event) {
        Session.getInstance().clearSession();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) menuButton.getScene().getWindow();

            stage.close();

            Stage loginStage = new Stage();
            loginStage.setScene(loginScene);
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error", AlertType.ERROR);
        }
    }

    @FXML
    private void initialize() {
        Session session = Session.getInstance();
        int id = session.getUserID();
        String username = session.getUsername();
    }
}
