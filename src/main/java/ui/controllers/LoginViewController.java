package ui.controllers;

import database.entity.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import session.LoadUserData;
import session.LoggedUser;
import validation.UserDataValidation;

import static ui.controllers.MainStage.getScene;

public class LoginViewController {


    //region Controls
    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    @FXML
    private Label lbWrongLoginData;
    //endregion

    User user;
    LoadUserData loadUserData = LoadUserData.getInstance();
    boolean connectedSuccessfully=false;

    @FXML
    private void initialize() {
       establishConnectionWithDatabase();
    }

    private void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> {loadUserData.establishConnection();
        connectedSuccessfully=true;
        });
        thread.start();
    }


    @FXML
    void btnLoginOnAction(ActionEvent event) {
        if(connectedSuccessfully) {
            user = loadUserData.loadUser(tfUsername.getText());
            if (areLoginDataValid()) {
                LoggedUser.getInstance().setLoggedUser(user);
                loadUserData.closeConnection();
                getMainStage().setScene(getScene("UserView.fxml"));
            }
        }
        else
        {
            establishConnectionWithDatabase();
        }

    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        loadUserData.closeConnection();
        getMainStage().setScene(getScene("RegisterView.fxml"));
    }

    private MainStage getMainStage() {
        return MainStage.getInstance();
    }


    private boolean areLoginDataValid()
    {
        if(user==null||!user.getPassword().equals(tfPassword.getText()))
        {
            showError();
            return false;
        }
        return true;
    }

    private void showError()
    {
        lbWrongLoginData.setText("The username or password you entered is incorrect");
    }

}