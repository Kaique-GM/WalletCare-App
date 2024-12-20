package com.financeX.controllers.Entries;

import java.math.BigDecimal;

import com.financeX.model.entities.Income;
import com.financeX.services.IncomeService;
import com.financeX.services.MonthService;
import com.financeX.services.Session;
import com.financeX.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEntriesController {

    private IncomeService service = new IncomeService();
    private MonthService service2 = new MonthService();
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

            Income income = new Income();
            income.setDescription(description);
            income.setValue(value);

            int userId = session.getUserID();
            int monthId = service2.getMonthId(this.currentMonth, this.year, userId);

            income.setId_user(userId);
            income.setId_month(monthId);
            income.setDate(new java.util.Date());

            service.insert(userId, monthId, income);

            Alerts.showAlert("Success", null, "Income entry successfully added.", AlertType.INFORMATION);

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
