package main.java.ui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.java.ui.stage.MainStage;

import java.io.IOException;

import static main.java.ui.stage.MainStage.getScene;

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

        getMainStage().setScene(getScene("UserView.fxml"));
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        getMainStage().setScene(getScene("RegisterView.fxml"));
    }

    private MainStage getMainStage()
    {
        return MainStage.getInstance();
    }
}