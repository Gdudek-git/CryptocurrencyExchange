package main.java.ui.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class RegisterViewController {


    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfConfirmPassword;

    @FXML
    private ComboBox cbxGender;

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

