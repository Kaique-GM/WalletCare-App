package com.financeX.controllers.Expenses;

import com.financeX.model.entities.Expenses;
import com.financeX.services.ExpenseService;
import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class UpdateExpensesController {

    private ExpenseService service = new ExpenseService();
    private Session session;

    @FXML
    private TextField ExpenseIdField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField valueField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    public void setSession(Session session) {
        this.session = session;
    }

    @FXML
    public void onConfirm(ActionEvent event) {
        try {
            String expenseIdText = ExpenseIdField.getText().trim();
            String description = descriptionField.getText().trim();
            String valueText = valueField.getText().trim();

            if (expenseIdText.isEmpty() || description.isEmpty() || valueText.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            int expenseId = Integer.parseInt(expenseIdText);
            BigDecimal value = new BigDecimal(valueText);

            Expenses expenses = new Expenses();
            expenses.setDescription(description);
            expenses.setDate(new java.util.Date());
            expenses.setValue(value);

            int userId = session.getUserID();
            service.update(userId, expenses, expenseId);

            Alerts.showAlert("Success", null, "Expense successfully updated.", AlertType.INFORMATION);

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            Alerts.showAlert("Invalid Input", null, "Expense ID and value must be valid numbers.", AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            Alerts.showAlert("Validation Error", null, e.getMessage(), AlertType.ERROR);
        } catch (Exception e) {
            Alerts.showAlert("Error", null, "An unexpected error occurred: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void onCancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
