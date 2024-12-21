package com.financeX.controllers;

import java.io.IOException;
import java.time.LocalDate;

import com.financeX.controllers.Entries.AddEntriesController;
import com.financeX.controllers.Entries.RemoveEntriesController;
import com.financeX.controllers.Entries.UpdateEntriesController;
import com.financeX.controllers.Expenses.AddFixedExpensesController;
import com.financeX.controllers.Expenses.AddVariableController;
import com.financeX.controllers.Expenses.RemoveExpensesController;
import com.financeX.controllers.Expenses.UpdateExpensesController;
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
            Alerts.showAlert("Error", null, "Error loading the add incomes screen", AlertType.ERROR);
        }
    }

    @FXML
    private void onAddFixedExpense(ActionEvent event) {
        String currentMonth = tabPane.getSelectionModel().getSelectedItem().getText();
        AddFixedExpensesController addFixedExpensesController;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/addFixedExpense.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            addFixedExpensesController = fxmlLoader.getController();
            addFixedExpensesController.setYear(year);
            addFixedExpensesController.setCurrentMonth(currentMonth);
            addFixedExpensesController.setSession(Session.getInstance());
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setScene(newScene);
            newStage.setTitle("Add Fixed Expenses");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the add fixed expenses screen", AlertType.ERROR);
        }
    }

    @FXML
    private void onAddVariableExpense(ActionEvent event) {
        String currentMonth = tabPane.getSelectionModel().getSelectedItem().getText();
        AddVariableController addVariableController;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/addVariableExpense.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            addVariableController = fxmlLoader.getController();
            addVariableController.setYear(year);
            addVariableController.setCurrentMonth(currentMonth);
            addVariableController.setSession(Session.getInstance());
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setScene(newScene);
            newStage.setTitle("Add Variable Expenses");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the add variable expenses screen", AlertType.ERROR);
        }
    }

    @FXML
    private void onRemoveEntries(ActionEvent event) {
        RemoveEntriesController removeEntriesController;
        try {
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

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the remove incomes screen", AlertType.ERROR);
        }
    }

    @FXML
    private void onRemoveExpense(ActionEvent event) {
        RemoveExpensesController removeFixedExpensesController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/removeExpense.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            removeFixedExpensesController = fxmlLoader.getController();
            removeFixedExpensesController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setScene(newScene);
            newStage.setTitle("Remove Fixed Expense");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the remove fixed Expense screen", AlertType.ERROR);
        }
    }

    @FXML
    private void onUpdateEntries(ActionEvent event) {
        UpdateEntriesController updateEntriesController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/updateEntries.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            updateEntriesController = fxmlLoader.getController();
            updateEntriesController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setScene(newScene);
            newStage.setTitle("Edit Entries");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the edit incomes screen", AlertType.ERROR);
        }
    }

    @FXML
    private void onUpdateExpense(ActionEvent event) {
        UpdateExpensesController updateFixedExpensesController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/updateExpenses.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            updateFixedExpensesController = fxmlLoader.getController();
            updateFixedExpensesController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setScene(newScene);
            newStage.setTitle("Edit Expenses");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the edit expenses screen", AlertType.ERROR);
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
