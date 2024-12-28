package com.financeX.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.financeX.model.entities.Expenses;
import com.financeX.model.entities.Income;
import com.financeX.model.entities.interfaces.FinancialRecord;
import com.financeX.services.ExpenseService;
import com.financeX.services.IncomeService;
import com.financeX.services.MonthService;
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
    private String month;
    private Integer userID;
    private IncomeService incomeService = new IncomeService();
    private ExpenseService expenseService = new ExpenseService();
    private MonthService monthService = new MonthService();
    private boolean isMenuVisible = true;

    @FXML
    private VBox menuPane;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> monthlyBarChart1;

    @FXML
    private BarChart<String, Number> monthlyBarChart2;

    @FXML
    private BarChart<String, Number> monthlyBarChart3;

    @FXML
    private CategoryAxis barXAxis;

    @FXML
    private CategoryAxis barXAxis1;

    @FXML
    private CategoryAxis barXAxis2;

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

    @FXML
    private Label totalLabel;

    @FXML
    private Label labelMonth;

    public void initialize() {
        session = Session.getInstance();
        userID = session.getUserID();
        year = LocalDate.now().getYear();
        labelYear.setText("" + year);

        configurePieChartHome();
        configuremonthlyBarChart1();
        configuremonthlyBarChart2();
        configuremonthlyBarChart3();
    }

    @FXML
    private void handleOverview() {
        labelMonth.setVisible(false);
        monthlyBarChart1.setVisible(true);
        monthlyBarChart3.setVisible(true);
        configurePieChartHome();
        configuremonthlyBarChart1();
        configuremonthlyBarChart2();
        configuremonthlyBarChart3();
        monthlyBarChart2.setPrefHeight(200);

    }

    @FXML
    private void handleMonthChange() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/monthChange.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change Year");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(labelYear.getScene().getWindow());
            dialogStage.setScene(scene);

            MonthChangeController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                labelMonth.setText(controller.getMonth());
            } else {
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load the month change dialog.", AlertType.ERROR);
        }

        labelMonth.setVisible(true);
        monthlyBarChart1.setVisible(false);
        monthlyBarChart3.setVisible(false);
        monthlyBarChart2.setPrefHeight(4500);

        month = labelMonth.getText();

        /// PieCharts
        int monthId = monthService.getMonthId(month, year, userID);
        List<Income> incomes = incomeService.getIncomes(userID, monthId);
        List<Expenses> fixedExpenses = expenseService.getExpenses(userID, monthId, 2);
        List<Expenses> variableExpenses = expenseService.getExpenses(userID, monthId, 3);

        BigDecimal bigTotalIn = calculateTotal(incomes);
        BigDecimal bigTotalFixedExp = calculateTotal(fixedExpenses);
        BigDecimal bigTotalVariableExp = calculateTotal(variableExpenses);

        Double totalIncome = bigTotalIn.doubleValue();
        Double totalFixedExp = bigTotalFixedExp.doubleValue();
        Double totalVariableExp = bigTotalVariableExp.doubleValue();

        Double total = totalIncome - totalFixedExp - totalVariableExp;

        pieChart.getData().clear();

        pieChart.getData().addAll(
                new PieChart.Data("Income: $" + totalIncome, totalIncome),
                new PieChart.Data("Fixed Expenses: $" + totalFixedExp, totalFixedExp),
                new PieChart.Data("Variable Expenses: $" + totalVariableExp, totalVariableExp));

        ///// BarCharts
        monthlyBarChart2.getData().clear();
        barXAxis1.setCategories(
                javafx.collections.FXCollections.observableArrayList("Income", "Fixed Expenses", "Variable Expenses"));

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income: $" + totalIncome.intValue());
        incomeSeries.getData().add(new XYChart.Data<>("Income", totalIncome));

        XYChart.Series<String, Number> fixedExpensesSeries = new XYChart.Series<>();
        fixedExpensesSeries.setName("Fixed: $" + totalFixedExp.intValue());
        fixedExpensesSeries.getData().add(new XYChart.Data<>("Fixed Expenses", totalFixedExp));

        XYChart.Series<String, Number> variableExpensesSeries = new XYChart.Series<>();
        variableExpensesSeries.setName("Variable: $" + totalVariableExp.intValue());
        variableExpensesSeries.getData().add(new XYChart.Data<>("Variable Expenses", totalVariableExp));

        monthlyBarChart2.getData().addAll(incomeSeries, fixedExpensesSeries, variableExpensesSeries);

        totalLabel.setText("Balance: $" + total);

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
            controller.setDialogStage(dialogStage);
            controller.setYear(year);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                year = controller.getYear();
                labelYear.setText(year.toString());
            } else {
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load the year change dialog.", AlertType.ERROR);
        }
        handleOverview();
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
    private void onAddNewYear() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addYear.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new Year");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(labelYear.getScene().getWindow());
            dialogStage.setScene(scene);

            AddYearController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserId(userID);
            controller.setYear(year);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                year = controller.getYear();
                labelYear.setText(year.toString());
            } else {
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load the Add new Year Screen.", AlertType.ERROR);
        }
        configurePieChartHome();
        configuremonthlyBarChart1();
        configuremonthlyBarChart2();
        configuremonthlyBarChart3();
    }

    @FXML
    private void onAddAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/add.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(labelYear.getScene().getWindow());
            dialogStage.setScene(scene);

            AddController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserId(userID);
            controller.setYear(year);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                handleOverview();
            } else {
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load the add dialog.", AlertType.ERROR);
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

    @FXML
    private void toggleMenu() {
        isMenuVisible = !isMenuVisible;
        menuPane.setVisible(isMenuVisible);
    }

    public void setYear(Integer year) {
        this.year = year;
        if (labelYear != null) {
            labelYear.setText(year.toString());
        }
    }

    private void configurePieChartHome() {
        pieChart.getData().clear();

        List<Income> incomes = incomeService.getAllIncomes(userID, year);
        List<Expenses> fixedExpenses = expenseService.getAllExpenses(userID, 2, year);
        List<Expenses> variableExpenses = expenseService.getAllExpenses(userID, 3, year);

        BigDecimal bigTotalIn = calculateTotal(incomes);
        BigDecimal bigTotalFixedExp = calculateTotal(fixedExpenses);
        BigDecimal bigTotalVariableExp = calculateTotal(variableExpenses);

        Double totalIncome = bigTotalIn.doubleValue();
        Double totalFixedExp = bigTotalFixedExp.doubleValue();
        Double totalVariableExp = bigTotalVariableExp.doubleValue();

        Double total = totalIncome - totalFixedExp - totalVariableExp;

        pieChart.getData().addAll(
                new PieChart.Data("Income: $" + totalIncome, totalIncome),
                new PieChart.Data("Fixed Exp: $" + totalFixedExp, totalFixedExp),
                new PieChart.Data("Variable Exp: $" + totalVariableExp, totalVariableExp));

        totalLabel.setText("Balance: $" + total);

    }

    private void configuremonthlyBarChart1() {
        String[] months = { "January", "February", "March", "April" };

        double[] totalIncome = new double[4];
        double[] totalFixedExpenses = new double[4];
        double[] totalVariableExpenses = new double[4];

        for (int i = 0; i < months.length; i++) {
            int monthId = monthService.getMonthId(months[i], year, userID);

            List<Income> incomes = incomeService.getIncomes(userID, monthId);
            List<Expenses> fixedExpenses = expenseService.getExpenses(userID, monthId, 2);
            List<Expenses> variableExpenses = expenseService.getExpenses(userID, monthId, 3);

            totalIncome[i] = calculateTotal(incomes).doubleValue();
            totalFixedExpenses[i] = calculateTotal(fixedExpenses).doubleValue();
            totalVariableExpenses[i] = calculateTotal(variableExpenses).doubleValue();
        }

        barXAxis.setCategories(
                javafx.collections.FXCollections.observableArrayList(months));

        // Limpa dados anteriores
        monthlyBarChart1.getData().clear();

        // Adiciona as séries ao gráfico
        addSeriesToChart1("Income", totalIncome);
        addSeriesToChart1("Fixed Expenses", totalFixedExpenses);
        addSeriesToChart1("Variable Expenses", totalVariableExpenses);
    }

    private void configuremonthlyBarChart2() {
        String[] months = { "May", "June", "July", "August" };

        double[] totalIncome = new double[4];
        double[] totalFixedExpenses = new double[4];
        double[] totalVariableExpenses = new double[4];

        for (int i = 0; i < months.length; i++) {
            int monthId = monthService.getMonthId(months[i], year, userID);

            List<Income> incomes = incomeService.getIncomes(userID, monthId);
            List<Expenses> fixedExpenses = expenseService.getExpenses(userID, monthId, 2);
            List<Expenses> variableExpenses = expenseService.getExpenses(userID, monthId, 3);

            totalIncome[i] = calculateTotal(incomes).doubleValue();
            totalFixedExpenses[i] = calculateTotal(fixedExpenses).doubleValue();
            totalVariableExpenses[i] = calculateTotal(variableExpenses).doubleValue();
        }

        barXAxis1.setCategories(
                javafx.collections.FXCollections.observableArrayList(months));

        // Limpa dados anteriores
        monthlyBarChart2.getData().clear();

        // Adiciona as séries ao gráfico
        addSeriesToChart2("Income", totalIncome);
        addSeriesToChart2("Fixed Expenses", totalFixedExpenses);
        addSeriesToChart2("Variable Expenses", totalVariableExpenses);
    }

    private void configuremonthlyBarChart3() {
        String[] months = { "September", "October", "November", "December" };

        double[] totalIncome = new double[4];
        double[] totalFixedExpenses = new double[4];
        double[] totalVariableExpenses = new double[4];

        for (int i = 0; i < months.length; i++) {
            int monthId = monthService.getMonthId(months[i], year, userID);

            List<Income> incomes = incomeService.getIncomes(userID, monthId);
            List<Expenses> fixedExpenses = expenseService.getExpenses(userID, monthId, 2);
            List<Expenses> variableExpenses = expenseService.getExpenses(userID, monthId, 3);

            totalIncome[i] = calculateTotal(incomes).doubleValue();
            totalFixedExpenses[i] = calculateTotal(fixedExpenses).doubleValue();
            totalVariableExpenses[i] = calculateTotal(variableExpenses).doubleValue();
        }

        barXAxis2.setCategories(
                javafx.collections.FXCollections.observableArrayList(months));

        // Limpa dados anteriores
        monthlyBarChart3.getData().clear();

        // Adiciona as séries ao gráfico
        addSeriesToChart3("Income", totalIncome);
        addSeriesToChart3("Fixed Expenses", totalFixedExpenses);
        addSeriesToChart3("Variable Expenses", totalVariableExpenses);
    }

    private BigDecimal calculateTotal(List<? extends FinancialRecord> list) {
        BigDecimal total = BigDecimal.ZERO;
        for (FinancialRecord record : list) {
            total = total.add(record.getValue());
        }
        return total;
    }

    private void addSeriesToChart1(String seriesName, double[] data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        String[] months = { "January", "February", "March", "April" };

        for (int i = 0; i < months.length; i++) {
            series.getData().add(new XYChart.Data<>(months[i], data[i]));
        }

        monthlyBarChart1.getData().add(series);
    }

    private void addSeriesToChart2(String seriesName, double[] data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        String[] months = { "May", "June", "July", "August" };

        for (int i = 0; i < months.length; i++) {
            series.getData().add(new XYChart.Data<>(months[i], data[i]));
        }

        monthlyBarChart2.getData().add(series);
    }

    private void addSeriesToChart3(String seriesName, double[] data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        String[] months = { "September", "October", "November", "December" };

        for (int i = 0; i < months.length; i++) {
            series.getData().add(new XYChart.Data<>(months[i], data[i]));
        }

        monthlyBarChart3.getData().add(series);
    }
}
