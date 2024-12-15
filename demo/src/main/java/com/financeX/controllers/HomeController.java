package com.financeX.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class HomeController {

    @FXML
    private TableView<Object> tableView;

    @FXML
    private TableColumn<Object, String> column1;

    @FXML
    private TableColumn<Object, String> column2;

    @FXML
    private TableColumn<Object, String> column3;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    // Método para lidar com o botão de adicionar
    @FXML
    private void onAdd(ActionEvent event) {
        System.out.println("Adicionar item");
        // Lógica para adicionar um item na tabela
    }

    // Método para lidar com o botão de editar
    @FXML
    private void onEdit(ActionEvent event) {
        System.out.println("Editar item");
        // Lógica para editar o item selecionado na tabela
    }

    // Método para lidar com o botão de deletar
    @FXML
    private void onDelete(ActionEvent event) {
        System.out.println("Deletar item");
        // Lógica para deletar o item selecionado na tabela
    }

    // Método para inicializar a tela, se necessário
    @FXML
    private void initialize() {
        System.out.println("HomeController inicializado");
        // Configuração inicial da tabela, se necessário
    }
}
