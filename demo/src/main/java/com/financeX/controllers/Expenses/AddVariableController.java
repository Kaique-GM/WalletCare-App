package com.financeX.controllers.Expenses;

import java.math.BigDecimal;

import com.financeX.model.entities.Expenses;
import com.financeX.services.ExpenseService;
import com.financeX.services.MonthService;
import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddVariableController {

    private ExpenseService service = new ExpenseService();
    private MonthService service2 = new MonthService();
    private ObservableList<Expenses> expenseList;
    private String currentMonth;
    private Session session;
    private Integer year;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField valueField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setExpensesList(ObservableList<Expenses> expensesList) {
        this.expenseList = expensesList;
    }

    @FXML
    public void onConfirm(ActionEvent event) {
        try {
            String description = descriptionField.getText().trim();
            String valueText = valueField.getText().trim();

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

            Expenses expense = new Expenses();
            expense.setDescription(description);
            expense.setValue(value);

            int userId = session.getUserID();
            int monthId = service2.getMonthId(this.currentMonth, this.year, userId);

            expense.setId_user(userId);
            expense.setId_month(monthId);
            expense.setDate(new java.util.Date());
            int category = 3;

            service.insert(userId, monthId, expense, category);
            expenseList.add(expense);

            Alerts.showAlert("Success", null, "Expense successfully added.", AlertType.INFORMATION);

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Alerts.showAlert("Invalid Input", null, e.getMessage(), AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Error", "An unexpected error occurred.", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void onCancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
