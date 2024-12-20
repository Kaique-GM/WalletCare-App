package com.financeX.controllers;

import java.math.BigDecimal;

import com.financeX.model.entities.Income;
import com.financeX.services.IncomeService;
import com.financeX.services.MonthService;
import com.financeX.services.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addEntriesController {

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
    public void OnConfirm(ActionEvent event) {
        Income income = new Income();

        income.setDescription(descriptionField.getText());
        income.setValue(new BigDecimal(valueField.getText()));
        int userId = session.getUserID();
        int monthId = service2.getMonthId(this.currentMonth, this.year, userId);
        income.setId_user(userId);
        income.setId_month(monthId);
        income.setDate(new java.util.Date());

        service.insert(userId, monthId, income);

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void Oncancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
