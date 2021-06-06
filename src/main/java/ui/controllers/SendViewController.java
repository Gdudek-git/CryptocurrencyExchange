package main.java.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.java.ui.stage.MainStage;

import static main.java.ui.stage.MainStage.getScene;

public class SendViewController {

    @FXML
    private TextField tfCurrencyAmount;

    @FXML
    private ComboBox cbxCryptocurrency;

    @FXML
    private TextField tfRecipientNickname;

    @FXML
    public void initialize()
    {
        setComboBoxItems();
    }

    @FXML
    void btnReturnOnClick(ActionEvent event) {
        getMainStage().setScene(getScene("UserView.fxml"));
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {

    }

    private void setComboBoxItems()
    {
        ObservableList<String> cryptocurrency = FXCollections.observableArrayList("BTC","ETH","DOGE");
        cbxCryptocurrency.setItems(cryptocurrency);
    }

    private MainStage getMainStage()
    {
        return MainStage.getInstance();
    }

}
