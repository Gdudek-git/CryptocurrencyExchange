package main.java.ui.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterViewController {


    //region Controllers
    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfCountry;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfEmail;

    @FXML
    private ComboBox cbxGender;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private PasswordField tfConfirmPassword;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TextField tfUsername;
    //endregion



    @FXML
    private void initialize()
    {
        setComboBoxItems();
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) {

    }

    private void setComboBoxItems()
    {
        ObservableList<String>gender = FXCollections.observableArrayList("Men","Women");
        cbxGender.setItems(gender);
    }


}

