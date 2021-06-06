package main.java.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.java.ui.stage.MainStage;

import static main.java.ui.stage.MainStage.getScene;

public class BuyViewController {

    @FXML
    private ComboBox cbxCryptocurrency;

    @FXML
    private ComboBox cbxCurrency;

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
    void btnBuyOnAction(ActionEvent event) {

    }

    @FXML
    void btnReturnOnClick(ActionEvent event) {
        getMainStage().setScene(getScene("UserView.fxml"));
    }

    private void setComboBoxItems()
    {
        ObservableList<String> currency = FXCollections.observableArrayList("PLN","USD","EUR");
        cbxCurrency.setItems(currency);
        ObservableList<String> cryptocurrency = FXCollections.observableArrayList("BTC","ETH","DOGE");
        cbxCryptocurrency.setItems(cryptocurrency);

    }

    private MainStage getMainStage()
    {
        return MainStage.getInstance();
    }

}
