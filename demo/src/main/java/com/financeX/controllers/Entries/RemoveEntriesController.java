package com.financeX.controllers.Entries;

import com.financeX.model.entities.interfaces.FinancialRecord;
import com.financeX.services.IncomeService;
import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveEntriesController {

    private IncomeService service = new IncomeService();
    private Session session;
    private ObservableList<FinancialRecord> incomeList;

    @FXML
    private TextField incomeIdField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    public void setSession(Session session) {
        this.session = session;
    }

    public void setIncomeList(ObservableList<FinancialRecord> incomeList) {
        this.incomeList = incomeList;
    }

    @FXML
    public void onConfirm(ActionEvent event) {
        try {
            String incomeIdText = incomeIdField.getText().trim();
            if (incomeIdText.isEmpty()) {
                throw new IllegalArgumentException("Income ID cannot be empty.");
            }

            int userId = session.getUserID();
            int income_id = Integer.parseInt(incomeIdText);

            service.delete(userId, income_id);

            FinancialRecord incomeToRemove = null;
            for (FinancialRecord income : incomeList) {
                if (income.getId() == income_id) {
                    incomeToRemove = income;
                    break;
                }
            }

            if (incomeToRemove != null) {
                incomeList.remove(incomeToRemove);
            }

            Alerts.showAlert("Success", null, "Income successfully removed.", AlertType.INFORMATION);

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alerts.showAlert("Invalid Input", null, "Income ID must be a valid number.", AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Alerts.showAlert("Invalid Input", null, "Income ID cannot be empty.", AlertType.ERROR);
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
