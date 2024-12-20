package com.financeX.controllers;

import java.io.IOException;
import java.time.LocalDate;

import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController {

    private int year;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label labelYear;

    @FXML
    private Label labelUser;

    @FXML
    private MenuButton menuButton;

    @FXML
    private void onAddEntries(ActionEvent event) {
        String currentMonth = tabPane.getSelectionModel().getSelectedItem().getText();
        AddEntriesController addController;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/addEntries.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            addController = fxmlLoader.getController();
            addController.setYear(year);
            addController.setCurrentMonth(currentMonth);
            addController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setScene(newScene);
            newStage.setTitle("Add Entries");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the add screen", AlertType.ERROR);
        }
    }

    @FXML
    private void onRemove(ActionEvent event) {
        RemoveEntriesController removeEntriesController;
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/removeEntries.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());

        removeEntriesController = fxmlLoader.getController();
        removeEntriesController.setSession(Session.getInstance());

        Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setScene(newScene);
            newStage.setTitle("Remove Entries");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        }catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the remove screen", AlertType.ERROR);
        }
    }

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
            Alerts.showAlert("Error", null, "Error loading the Login screen", AlertType.ERROR);
        }
    }

    @FXML
    public void onYearSelected(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        String selectedYearText = selectedItem.getText();

        try {
            year = Integer.parseInt(selectedYearText);
            labelYear.setText("" + year);
        } catch (NumberFormatException e) {
            Alerts.showAlert("Error", null, "Invalid year selected", AlertType.ERROR);
        }
    }

    @FXML
    private void initialize() {
        Session session = Session.getInstance();
        int user_id = session.getUserID();
        String username = session.getUsername();

        year = LocalDate.now().getYear();
        labelYear.setText("" + year);
        labelUser.setText(username);
    }
}
