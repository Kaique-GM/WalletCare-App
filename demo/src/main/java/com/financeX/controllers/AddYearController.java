package com.financeX.controllers;

import com.financeX.services.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddYearController {

    @FXML
    private TextField yearTextField;

    private Stage dialogStage;
    private boolean okClicked = false;
    private Integer year;
    private UserService service = new UserService();
    private Integer userId;

    // Confirma a entrada do ano
    @FXML
    private void handleConfirm() {
        try {
            year = Integer.parseInt(yearTextField.getText());
            service.insertYear(year, userId);
            okClicked = true;
            dialogStage.close();
        } catch (NumberFormatException e) {
            System.out.println("Invalid year entered!");
        }
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setYear(Integer year) {
        this.year = year;
        if (yearTextField != null) {
            yearTextField.setText(year.toString());
        }
    }

    public int getYear() {
        return year;
    }
}
