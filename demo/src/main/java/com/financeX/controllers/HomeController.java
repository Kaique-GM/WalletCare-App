package com.financeX.controllers;

import java.io.IOException;
import java.time.LocalDate;

import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController {
    
    private Session session;
    private Integer year;
    private Integer userID;

    @FXML
    private VBox menuPane;

    @FXML
    private PieChart expensesPieChart;

    @FXML
    private BarChart<String, Number> revenueBarChart;

    @FXML
    private CategoryAxis barXAxis;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btLogout;

    @FXML
    private Button btnMonthChange;

    @FXML
    private Button btnYearChange;

    @FXML
    private Button btnTablesScreen;

    @FXML
    private Label labelYear;

    private boolean isMenuVisible = true;

    public void initialize() {
        session = Session.getInstance();
        userID = session.getUserID();
        year = LocalDate.now().getYear();
        labelYear.setText("" + year);

        configureExpensesPieChart();
        configureRevenueBarChart();
    }

    private void configureExpensesPieChart() {
        expensesPieChart.getData().addAll(
                new PieChart.Data("Rent", 35),
                new PieChart.Data("Utilities", 20),
                new PieChart.Data("Salaries", 25),
                new PieChart.Data("Miscellaneous", 20));
    }

    private void configureRevenueBarChart() {
        barXAxis.setCategories(javafx.collections.FXCollections.observableArrayList("January", "February", "March"));

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        incomeSeries.getData().add(new XYChart.Data<>("January", 8000));
        incomeSeries.getData().add(new XYChart.Data<>("February", 9500));
        incomeSeries.getData().add(new XYChart.Data<>("March", 11000));

        XYChart.Series<String, Number> fixedExpensesSeries = new XYChart.Series<>();
        fixedExpensesSeries.setName("Fixed Expenses");
        fixedExpensesSeries.getData().add(new XYChart.Data<>("January", 4000));
        fixedExpensesSeries.getData().add(new XYChart.Data<>("February", 4200));
        fixedExpensesSeries.getData().add(new XYChart.Data<>("March", 4500));

        XYChart.Series<String, Number> variableExpensesSeries = new XYChart.Series<>();
        variableExpensesSeries.setName("Variable Expenses");
        variableExpensesSeries.getData().add(new XYChart.Data<>("January", 1500));
        variableExpensesSeries.getData().add(new XYChart.Data<>("February", 1800));
        variableExpensesSeries.getData().add(new XYChart.Data<>("March", 2000));

        revenueBarChart.getData().addAll(incomeSeries, fixedExpensesSeries, variableExpensesSeries);
    }

    @FXML
    private void toggleMenu() {
        isMenuVisible = !isMenuVisible;
        menuPane.setVisible(isMenuVisible);
    }

    @FXML
    private void handleOverview() {
        System.out.println("🏠 Overview selected.");
    }

    @FXML
    private void handleMonthChange() {
        System.out.println("📅 Change Month selected.");
    }

    @FXML
    private void handleYearChange() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/yearChange.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change Year");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(labelYear.getScene().getWindow());
            dialogStage.setScene(scene);

            YearChangeController controller = loader.getController();
            controller.setYear(year);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                year = controller.getYear();
                labelYear.setText("" + year);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load the year change dialog.", AlertType.ERROR);
        }
    }

    @FXML
    private void handleTablesScreen() {
        TablesController tablesController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tables.fxml"));
            Scene scene = new Scene(loader.load());

            tablesController = loader.getController();
            tablesController.setUserId(userID);
            tablesController.setYear(year);

            Stage stage = (Stage) btnTablesScreen.getScene().getWindow();
            stage.close();

            Stage tableStage = new Stage();
            tableStage.setScene(scene);
            tableStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error", AlertType.ERROR);

        }

    }

    @FXML
    private void onLogout(ActionEvent event) {
        Session.getInstance().clearSession();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) btLogout.getScene().getWindow();

            stage.close();

            Stage loginStage = new Stage();
            loginStage.setScene(loginScene);
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the Login screen", AlertType.ERROR);
        }
    }
}
