package com.financeX.controllers;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.financeX.model.entities.Expenses;
import com.financeX.model.entities.Income;
import com.financeX.services.ExpenseService;
import com.financeX.services.IncomeService;
import com.financeX.services.MonthService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddController {

    private Stage dialogStage;
    private boolean okClicked = false;
    private String month;
    private String typeString;
    private Integer typeInt;
    private Integer userId;
    private Integer monthId;
    private Integer year;
    private IncomeService incomeService = new IncomeService();
    private ExpenseService expenseService = new ExpenseService();
    private MonthService monthService = new MonthService();

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField valueTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    public void initialize() {
        monthComboBox.setItems(FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"));

        typeComboBox.setItems(FXCollections.observableArrayList("Income", "Fixed Expense", "Variable Expense"));

        typeComboBox.getSelectionModel().select(0);
        monthComboBox.getSelectionModel().select(0);
    }

    @FXML
    private void handleConfirm() {
        month = monthComboBox.getValue();
        typeString = typeComboBox.getValue();
        monthId = monthService.getMonthId(month, year, userId);
        String valueText = valueTextField.getText().trim();
        String description = descriptionTextField.getText().trim();

        if (description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        if (valueText.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be empty.");
        }

        BigDecimal value;
        try {
            value = new BigDecimal(valueText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value must be a valid number.");
        }

        switch (typeString) {
            case "Income":
                Income income = new Income();
                income.setDescription(description);
                income.setValue(value);
                income.setId_user(userId);
                income.setDate(new java.util.Date());
                income.setId_month(monthId);

                incomeService.insert(userId, monthId, income);
                break;
            case "Fixed Expense":
                Expenses expense = new Expenses();
                expense.setDescription(description);
                expense.setValue(value);
                expense.setId_user(userId);
                expense.setDate(new java.util.Date());
                expense.setId_month(monthId);

                expenseService.insert(userId, monthId, expense, 2);
                break;
            case "Variable Expense":
                Expenses VariableExpense = new Expenses();
                VariableExpense.setDescription(description);
                VariableExpense.setValue(value);
                VariableExpense.setId_user(userId);
                VariableExpense.setDate(new java.util.Date());
                VariableExpense.setId_month(monthId);

                expenseService.insert(userId, monthId, VariableExpense, 3);
                break;
            default:
                break;
        }

        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}