package com.financeX.controllers.Expenses;

import com.financeX.services.ExpenseService;
import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveFixedExpensesController {

    private ExpenseService service = new ExpenseService();
    private Session session;

    @FXML
    private TextField expenseIdField;

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
            String expenseIdText = expenseIdField.getText().trim();
            if (expenseIdText.isEmpty()) {
                throw new IllegalArgumentException("Expense ID cannot be empty.");
            }

            int userId = session.getUserID();
            int expense_id = Integer.parseInt(expenseIdText);

            service.delete(userId, expense_id);

            Alerts.showAlert("Success", null, "Expense successfully removed.", AlertType.INFORMATION);
            
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alerts.showAlert("Invalid Input", null, "Expense ID must be a valid number.", AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Alerts.showAlert("Invalid Input", null, "Expense ID cannot be empty.", AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "An unexpected error occurred.", AlertType.ERROR);
        }
    }

    @FXML
    public void onCancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
