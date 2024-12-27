package com.financeX.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.financeX.controllers.Entries.AddEntriesController;
import com.financeX.controllers.Entries.RemoveEntriesController;
import com.financeX.controllers.Entries.UpdateEntriesController;
import com.financeX.controllers.Expenses.AddFixedExpensesController;
import com.financeX.controllers.Expenses.AddVariableController;
import com.financeX.controllers.Expenses.RemoveExpensesController;
import com.financeX.controllers.Expenses.UpdateExpensesController;
import com.financeX.model.entities.Expenses;
import com.financeX.model.entities.Income;
import com.financeX.model.entities.interfaces.FinancialRecord;
import com.financeX.services.ExpenseService;
import com.financeX.services.IncomeService;
import com.financeX.services.MonthService;
import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TablesController {

    private IncomeService incomeService = new IncomeService();
    private MonthService monthService = new MonthService();
    private ExpenseService expenseService = new ExpenseService();
    private Integer year;
    private Integer userId;
    private boolean isMenuVisible = true;
    private ObservableList<FinancialRecord> financialList = FXCollections.observableArrayList();

    @FXML
    private VBox menuPane;

    @FXML
    private VBox operationPane;

    @FXML
    private Button btLogout;

    @FXML
    private Button btOverview;

    @FXML
    private Label labelYear;

    @FXML
    private Label categoryName;

    @FXML
    private Label monthName;

    @FXML
    private TableView<FinancialRecord> dataTable;

    @FXML
    private TableColumn<FinancialRecord, Integer> idColumn;

    @FXML
    private TableColumn<FinancialRecord, String> descriptionColumn;

    @FXML
    private TableColumn<FinancialRecord, BigDecimal> valueColumn;

    @FXML
    private TableColumn<FinancialRecord, LocalDate> dateColumn;

    public void setYear(Integer year) {
        this.year = year;
        if (labelYear != null) {
            labelYear.setText(year.toString());
        }
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueColumn.setCellFactory(column -> new javafx.scene.control.TableCell<FinancialRecord, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("$" + item.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                }
            }
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        dataTable.setItems(financialList);

    }

    public void loadIncomes() {
        categoryName.setText("Incomes");
        String month = monthName.getText();
        int monthId = monthService.getMonthId(month, year, userId);

        try {
            List<Income> incomes = incomeService.getIncomes(userId, monthId);
            financialList.setAll(incomes);

        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load incomes", AlertType.ERROR);
        }
    }

    public void loadFixedExpenses() {
        categoryName.setText("Fixed Expenses");
        String month = monthName.getText();
        int monthId = monthService.getMonthId(month, year, userId);

        try {
            List<Expenses> expenses = expenseService.getExpenses(userId, monthId, 2);
            financialList.setAll(expenses);

        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load incomes", AlertType.ERROR);
        }
    }

    public void loadVariableExpenses() {
        categoryName.setText("Variable Expenses");
        String month = monthName.getText();
        int monthId = monthService.getMonthId(month, year, userId);

        try {
            List<Expenses> expenses = expenseService.getExpenses(userId, monthId, 3);
            financialList.setAll(expenses);

        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load incomes", AlertType.ERROR);
        }
    }

    @FXML
    private void handleOverview() {
        HomeController homeController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Scene homeScene = new Scene(fxmlLoader.load());

            homeController = fxmlLoader.getController();
            homeController.setYear(year);

            Stage stage = (Stage) btOverview.getScene().getWindow();
            stage.close();

            Stage homeStage = new Stage();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(true);
            homeStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the Overview screen", AlertType.ERROR);
        }
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
                monthName.setText(controller.getMonth());
            }

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load the month change dialog.", AlertType.ERROR);
        }
        loadIncomes();
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

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                year = controller.getYear();
                labelYear.setText(year.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load the year change dialog.", AlertType.ERROR);
        }
        loadIncomes();
    }

    @FXML
    private void handleNewYear() {
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
            controller.setUserId(userId);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                year = controller.getYear();
                labelYear.setText(year.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Failed to load the add new year screen.", AlertType.ERROR);
        }
        loadIncomes();
    }

    @FXML
    private void handleIncomeView() {
        loadIncomes();
    }

    @FXML
    private void handleFixedExpensesView() {
        loadFixedExpenses();
    }

    @FXML
    private void handleVariableExpensesView() {
        loadVariableExpenses();
    }

    @FXML
    private void handleAddIncome() {
        String month = monthName.getText();
        AddEntriesController addController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/addEntries.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            addController = fxmlLoader.getController();
            addController.setYear(year);
            addController.setCurrentMonth(month);
            addController.setIncomeList(financialList);
            addController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Add Entries");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the add incomes screen", AlertType.ERROR);
        }
        loadIncomes();
    }

    @FXML
    private void handleRemoveIncome() {
        RemoveEntriesController removeEntriesController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/removeEntries.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            removeEntriesController = fxmlLoader.getController();
            removeEntriesController.setIncomeList(financialList);
            removeEntriesController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Remove Entries");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the remove incomes screen", AlertType.ERROR);
        }
        loadIncomes();
    }

    @FXML
    private void handleEditIncome() {
        UpdateEntriesController updateEntriesController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/updateEntries.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            updateEntriesController = fxmlLoader.getController();
            updateEntriesController.setIncomeList(financialList);
            updateEntriesController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Edit Entries");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the edit incomes screen", AlertType.ERROR);
        }
        loadIncomes();
    }

    @FXML
    private void handleAddFixedExpense() {
        String month = monthName.getText();
        AddFixedExpensesController addFixedExpensesController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/addFixedExpense.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            addFixedExpensesController = fxmlLoader.getController();
            addFixedExpensesController.setYear(year);
            addFixedExpensesController.setCurrentMonth(month);
            addFixedExpensesController.setExpensesList(financialList);
            addFixedExpensesController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Add Fixed Expense");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the add fixed Expense screen", AlertType.ERROR);
        }
        loadFixedExpenses();
    }

    @FXML
    private void handleRemoveFixedExpense() {
        RemoveExpensesController removeExpensesController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/removeExpense.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            removeExpensesController = fxmlLoader.getController();
            removeExpensesController.setExpenseFixedList(financialList);
            removeExpensesController.setExpenseVariableList(financialList);
            removeExpensesController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Remove Expense");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the remove expenses screen", AlertType.ERROR);
        }
        loadFixedExpenses();
    }

    @FXML
    private void handleEditFixedExpense() {
        UpdateExpensesController updateExpensesController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/updateExpenses.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            updateExpensesController = fxmlLoader.getController();
            updateExpensesController.setExpenseFixedList(financialList);
            updateExpensesController.setExpenseVariableList(financialList);
            updateExpensesController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Edit Expense");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the edit expenses screen", AlertType.ERROR);
        }
        loadFixedExpenses();
    }

    @FXML
    private void handleAddVariableExpense() {
        String month = monthName.getText();
        AddVariableController addVariableController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/addVariableExpense.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            addVariableController = fxmlLoader.getController();
            addVariableController.setYear(year);
            addVariableController.setCurrentMonth(month);
            addVariableController.setExpensesList(financialList);
            addVariableController.setSession(Session.getInstance());

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Add Variable Expense");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error loading the add variable Expense screen", AlertType.ERROR);
        }
        loadVariableExpenses();
    }

    @FXML
    private void handleRemoveVariableExpense() {
        handleRemoveFixedExpense();
        loadVariableExpenses();
    }

    @FXML
    private void handleEditVariableExpense() {
        handleEditFixedExpense();
        loadVariableExpenses();
    }

    @FXML
    private void toggleMenu() {
        isMenuVisible = !isMenuVisible;
        menuPane.setVisible(isMenuVisible);
        operationPane.setVisible(isMenuVisible);
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
