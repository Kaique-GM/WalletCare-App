package com.financeX.controllers.MenuButtons;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class MonthChangeController {

    @FXML
    private ComboBox<String> monthComboBox;

    private Stage dialogStage;
    private boolean okClicked = false;
    private String month;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public String getMonth() {
        return month;
    }

    @FXML
    public void initialize() {
        monthComboBox.setItems(FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"));

        monthComboBox.getSelectionModel().select(0);

    }

    @FXML
    private void handleConfirm() {
        this.month = monthComboBox.getValue();
        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }
}