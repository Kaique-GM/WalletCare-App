package com.financeX.controllers;

import java.io.IOException;

import com.financeX.services.UserService;
import com.financeX.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    private UserService service = new UserService();

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    Button loginButton;

    @FXML
    Button registerButton;

    @FXML void onLogin(ActionEvent event){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            Alerts.showAlert("Error", null, "Os campos estão vazios", AlertType.ERROR);
            return;
        }

        try{

            Boolean userExists = service.userExists(username);
            Boolean passwordMatches = service.passwordMatches(password);

            if(userExists && passwordMatches){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/financeX/views/Home.fxml"));
                Scene homeScene = new Scene(fxmlLoader.load());

                Stage stage = (Stage) loginButton.getScene().getWindow();

                stage.close();

                Stage homeStage = new Stage();
                homeStage.setScene(homeScene);
                homeStage.show();
            }
            
            else if(!userExists){
                Alerts.showAlert("Error", null, "Usuário não encontrado", AlertType.ERROR);
            return;
            }
            else if(!passwordMatches){
                Alerts.showAlert("Error", null, "Senha incorreta", AlertType.ERROR);
            return;
            }
        }catch(IOException e){
            e.printStackTrace();
            Alerts.showAlert("Error", null, "Error", AlertType.ERROR);

        }
    }
}