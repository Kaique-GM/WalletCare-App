package com.financeX.controllers.Entries;

import com.financeX.model.entities.Income;
import com.financeX.model.entities.interfaces.FinancialRecord;
import com.financeX.services.IncomeService;
import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class UpdateEntriesController {

    private IncomeService service = new IncomeService();
    private Session session;
    private ObservableList<FinancialRecord> incomeList;

    @FXML
    private TextField incomeIdField;

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

    public void setIncomeList(ObservableList<FinancialRecord> incomeList) {
        this.incomeList = incomeList;
    }

    @FXML
    public void onConfirm(ActionEvent event) {
        try {
            String incomeIdText = incomeIdField.getText().trim();
            String description = descriptionField.getText().trim();
            String valueText = valueField.getText().trim();

            if (incomeIdText.isEmpty() || description.isEmpty() || valueText.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            int incomeId = Integer.parseInt(incomeIdText);
            BigDecimal value = new BigDecimal(valueText);

            Income income = new Income();
            income.setDescription(description);
            income.setDate(new java.util.Date());
            income.setValue(value);
            income.setId(incomeId);

            int userId = session.getUserID();
            service.update(userId, income, incomeId);

            for (int i = 0; i < incomeList.size(); i++) {
                FinancialRecord IncomeAux = incomeList.get(i);
                if (IncomeAux.getId() == incomeId) {
                    incomeList.set(i, income);
                    break;
                }
            }

            Alerts.showAlert("Success", null, "Income successfully updated.", AlertType.INFORMATION);

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            Alerts.showAlert("Invalid Input", null, "Income ID and value must be valid numbers.", AlertType.ERROR);
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
