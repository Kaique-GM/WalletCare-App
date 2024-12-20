package com.financeX.controllers.Entries;

import com.financeX.services.IncomeService;
import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveEntriesController {

    private IncomeService service = new IncomeService();
    private Session session;

    @FXML
    private TextField incomeIdField;

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
            String incomeIdText = incomeIdField.getText().trim();
            if (incomeIdText.isEmpty()) {
                throw new IllegalArgumentException("Income ID cannot be empty.");
            }

            int userId = session.getUserID();
            int income_id = Integer.parseInt(incomeIdText);

            service.delete(userId, income_id);

            Alerts.showAlert("Success", null, "Income entry successfully removed.", AlertType.INFORMATION);
            
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
