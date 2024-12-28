package com.financeX.utils;

// Imports necessary JavaFX classes for alert dialogs
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

    public static void showAlert(String title, String header, String content, AlertType alertType) {
        Alert alert = new Alert(alertType); // Creates an alert
        alert.setTitle(title); // Sets the alert title
        alert.setHeaderText(header); // Sets the header text
        alert.setContentText(content); // Sets the content/message
        alert.showAndWait(); // Displays the alert
    }
}
