package com.financeX.controllers.Expenses;

import com.financeX.model.entities.Expenses;
import com.financeX.services.ExpenseService;
import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveExpensesController {

    private ExpenseService service = new ExpenseService();
    private ObservableList<Expenses> expenseFixedList;
    private ObservableList<Expenses> expenseVariableList;
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

    public void setExpenseFixedList(ObservableList<Expenses> expenseFixedList) {
        this.expenseFixedList = expenseFixedList;
    }

    public void setExpenseVariableList(ObservableList<Expenses> expenseVariableList) {
        this.expenseVariableList = expenseVariableList;
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

            Expenses expenseToRemove = null;

            for (Expenses expenseAux : expenseFixedList) {
                if (expenseAux.getId() == expense_id) {
                    expenseToRemove = expenseAux;
                    break;
                }
            }

            for (Expenses expenseAux : expenseVariableList) {
                if (expenseAux.getId() == expense_id) {
                    expenseToRemove = expenseAux;
                    break;
                }
            }

            if (expenseToRemove != null) {
                expenseFixedList.remove(expenseToRemove);
                expenseVariableList.remove(expenseToRemove);
            } else {
                Alerts.showAlert("Error", null, "Expense not found in the lists.", AlertType.ERROR);
                return;
            }

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
