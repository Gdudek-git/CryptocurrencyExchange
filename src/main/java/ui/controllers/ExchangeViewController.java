package main.java.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ExchangeViewController {


    @FXML
    private ComboBox cbxTargetCurrency;

    @FXML
    private ComboBox cbxCurrentCurrency;

    @FXML
    private TextField tfCurrencyAmount;

    @FXML
    private TextField tfCryptocurrencyAmount;

    @FXML
    private TextField tfCurrencyPriceInfo;

    @FXML
    public void initialize()
    {
        setComboBoxItems();
    }

    @FXML
    void btnExchangeOnAction(ActionEvent event) {

    }

    @FXML
    void btnReturnOnClick(ActionEvent event) {

    }

    private void setComboBoxItems()
    {
        ObservableList<String> currency = FXCollections.observableArrayList("PLN","USD","EUR");
        cbxCurrentCurrency.setItems(currency);
        cbxTargetCurrency.setItems(currency);


    }

}
