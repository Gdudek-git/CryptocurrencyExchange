package ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import static ui.controllers.MainStage.getScene;

public class SellViewController {

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
    public void initialize() {
        setComboBoxItems();
    }

    @FXML
    void btnReturnOnClick(ActionEvent event) {
        getMainStage().setScene(getScene("UserView.fxml"));
    }

    @FXML
    void btnSellOnAction(ActionEvent event) {

    }

    private void setComboBoxItems() {
        ObservableList<String> currency = FXCollections.observableArrayList("PLN", "USD", "EUR");
        cbxCurrency.setItems(currency);
        ObservableList<String> cryptocurrency = FXCollections.observableArrayList("BTC", "ETH", "DOGE");
        cbxCryptocurrency.setItems(cryptocurrency);

    }

    private MainStage getMainStage() {
        return MainStage.getInstance();
    }


}
