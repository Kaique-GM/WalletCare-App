package com.financeX.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.financeX.controllers.Entries.AddEntriesController;
import com.financeX.controllers.Entries.RemoveEntriesController;
import com.financeX.controllers.Entries.UpdateEntriesController;
import com.financeX.controllers.Expenses.AddFixedExpensesController;
import com.financeX.controllers.Expenses.AddVariableController;
import com.financeX.controllers.Expenses.RemoveExpensesController;
import com.financeX.controllers.Expenses.UpdateExpensesController;
import com.financeX.model.entities.Expenses;
import com.financeX.model.entities.Income;
import com.financeX.services.ExpenseService;
import com.financeX.services.IncomeService;
import com.financeX.services.MonthService;
import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController {

    private int year;

    private ObservableList<Income> incomeList = FXCollections.observableArrayList();
    private ObservableList<Expenses> fixedExpensesList = FXCollections.observableArrayList();
    private ObservableList<Expenses> variableExpensesList = FXCollections.observableArrayList();

    private IncomeService incomeService = new IncomeService();
    private ExpenseService expenseService = new ExpenseService();
    private MonthService monthService = new MonthService();

    @FXML
    private TabPane tabPane;
    @FXML
    private Label labelYear;
    @FXML
    private Label labelUser;
    @FXML
    private MenuButton menuButton;
    @FXML
    private TableView<Income> tableEntries;
    @FXML
    private TableView<Expenses> tableFixedExpenses;
    @FXML
    private TableView<Expenses> tableVariableExpenses;
    @FXML
    private TableColumn<Expenses, Integer> idVariableExpenseColumn;
    @FXML
    private TableColumn<Expenses, String> descriptionVariableExpenseColumn;
    @FXML
    private TableColumn<Expenses, BigDecimal> valueVariableExpenseColumn;
    @FXML
    private TableColumn<Expenses, String> dateVariableExpenseColumn;
    @FXML
    private TableColumn<Expenses, Integer> idExpenseColumn;
    @FXML
    private TableColumn<Expenses, String> descriptionExpenseColumn;
    @FXML
    private TableColumn<Expenses, BigDecimal> valueExpenseColumn;
    @FXML
    private TableColumn<Expenses, String> dateExpenseColumn;
    @FXML
    private TableColumn<Income, Integer> idColumn;
    @FXML
    private TableColumn<Income, String> descriptionColumn;
    @FXML
    private TableColumn<Income, BigDecimal> valueColumn;
    @FXML
    private TableColumn<Income, String> dateColumn;

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
            addController.setIncomeList(incomeList);

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
            addFixedExpensesController.setExpensesList(fixedExpensesList);


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
            addVariableController.setExpensesList(variableExpensesList);

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
            removeEntriesController.setIncomeList(incomeList);
            

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
        RemoveExpensesController removeExpensesController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/removeExpense.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            removeExpensesController = fxmlLoader.getController();
            removeExpensesController.setSession(Session.getInstance());
            removeExpensesController.setExpenseFixedList(fixedExpensesList);
            removeExpensesController.setExpenseVariableList(variableExpensesList);

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
            updateEntriesController.setIncomeList(incomeList);


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
            updateFixedExpensesController.setExpenseFixedList(fixedExpensesList);
            updateFixedExpensesController.setExpenseVariableList(variableExpensesList);

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

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (oldTab != newTab) {
                String currentMonth = newTab.getText();
                int monthId = monthService.getMonthId(currentMonth, year, user_id);

                loadIncomes(user_id, monthId);
                loadFixedExpenses(user_id, monthId, 2);
                loadVariableExpenses(user_id, monthId, 3);

            }
        });

        setCellValues();
    }

    private void loadIncomes(Integer userId, Integer monthId) {
        incomeList.clear();
        incomeList.addAll(incomeService.getIncomes(userId, monthId));
        tableEntries.setItems(incomeList);
    }

    private void loadFixedExpenses(Integer userId, Integer monthId, Integer category) {
        fixedExpensesList.clear();
        fixedExpensesList.addAll(expenseService.getExpenses(userId, monthId, category));
        tableFixedExpenses.setItems(fixedExpensesList);
    }

    private void loadVariableExpenses(Integer userId, Integer monthId, Integer category) {
        variableExpensesList.clear();
        variableExpensesList.addAll(expenseService.getExpenses(userId, monthId, category));
        tableVariableExpenses.setItems(variableExpensesList);
    }

    private void setCellValues() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateColumn.setCellValueFactory(cellData -> {
            Income income = cellData.getValue();
            return new SimpleStringProperty(formatDateForDisplay(income.getDate()));
        });

        idExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateExpenseColumn.setCellValueFactory(cellData -> {
            Expenses expense = cellData.getValue();
            return new SimpleStringProperty(formatDateForDisplay(expense.getDate()));
        });

        idVariableExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionVariableExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueVariableExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateVariableExpenseColumn.setCellValueFactory(cellData -> {
            Expenses expense = cellData.getValue();
        return new SimpleStringProperty(formatDateForDisplay(expense.getDate()));
    });
    }

    private String formatDateForDisplay(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } else {
            return "";
        }
    }
}
