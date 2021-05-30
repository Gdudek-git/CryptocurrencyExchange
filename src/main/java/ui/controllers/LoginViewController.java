package main.java.ui.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginViewController
{
    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    @FXML
    void btnExitOnAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {

    }
    @FXML
    void btnRegisterOnAction(ActionEvent event) {

    }
}