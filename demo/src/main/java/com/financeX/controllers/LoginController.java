package com.financeX.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    // Método chamado ao clicar no botão de login
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validação simples
        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Please fill in both fields.");
        } else {
            // Aqui você pode adicionar a lógica para autenticar o usuário (consultar o banco, por exemplo)
            // Para fins de exemplo, vamos assumir que o login é sempre bem-sucedido se os campos não estiverem vazios
            if (username.equals("admin") && password.equals("admin123")) {
                errorMessage.setText("Login successful!");
                // Redirecionar para outra tela, por exemplo
            } else {
                errorMessage.setText("Invalid username or password.");
            }
        }
    }

    // Método para limpar a mensagem de erro quando o usuário começa a digitar
    @FXML
    public void clearErrorMessage(KeyEvent event) {
        errorMessage.setText("");
    }
}