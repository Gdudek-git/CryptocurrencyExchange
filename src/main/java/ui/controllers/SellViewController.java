package ui.controllers;

import cryptocurrency.CryptocurrencyExchangeRates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import services.RequestToSellCryptocurrency;
import validation.SellViewValidation;
import validation.Valid;

import static ui.controllers.MainStage.getScene;

public class SellViewController {

    //region Controllers

    @FXML
    private ComboBox<String> cbxCryptocurrency;

    @FXML
    private ComboBox<String> cbxCurrency;

    @FXML
    private TextField tfCurrencyAmount;

    @FXML
    private TextField tfCryptocurrencyAmount;

    @FXML
    private TextField tfCurrentCryptocurrencyRates;

    @FXML
    private TextField tfCurrentCurrencyRates;

    @FXML
    private Label lbInfo;
    //endregion


    private String selectedCryptocurrency="BTC";
    private String selectedCurrency="PLN";
    private int selectedCurrencyIndex=0;
    private SellViewValidation sellValidation = SellViewValidation.getInstance();


    @FXML
    public void initialize() {
        RequestToSellCryptocurrency.getInstance().establishConnectionWithDatabase();
        updateRates();
        setComboBoxItems();
        setUI();
    }

    @FXML
    void btnReturnOnClick(ActionEvent event)
    {
        RequestToSellCryptocurrency.getInstance().closeConnectionWithDatabase();
        getMainStage().setScene(getScene("UserView.fxml"));
    }

    @FXML
    void btnSellOnAction(ActionEvent event) {
        resetLabel();
        if(checkIfDouble())
        {
        if(checkIfSufficientFunds()) {
            sellCryptocurrency();
        }
    }
    }

    @FXML
    void cbxCryptocurrencyOnAction(ActionEvent event) {
        selectedCryptocurrency = cbxCryptocurrency.getSelectionModel().getSelectedItem();
        updateRates();
        setUI();
        showAmountUserCanGetFromSelling();
    }

    @FXML
    void cbxCurrencyOnAction(ActionEvent event) {

        switch(cbxCurrency.getSelectionModel().getSelectedItem()){
            case "PLN":selectedCurrencyIndex=0;break;
            case "EUR":selectedCurrencyIndex=1;break;
            case "USD":selectedCurrencyIndex=2;break;
        }
        selectedCurrency=cbxCurrency.getSelectionModel().getSelectedItem();
        setUI();
        showAmountUserCanGetFromSelling();

    }



   public boolean checkIfDouble()
    {
        if(!sellValidation.checkIfDouble(tfCryptocurrencyAmount.getText()).equals(Valid.VALID))
        {
            showInfo(sellValidation.checkIfDouble(tfCryptocurrencyAmount.getText()));
            return false;
        }
        return true;

    }

    private boolean checkIfSufficientFunds()
    {
        if(!sellValidation.checkIfSufficientFundsToSell(selectedCryptocurrency,tfCryptocurrencyAmount.getText()).equals(Valid.VALID))
        {
            showInfo(sellValidation.checkIfSufficientFundsToSell(selectedCryptocurrency,tfCryptocurrencyAmount.getText()));
            return false;
        }
        return true;
    }

    private void sellCryptocurrency()
    {
        RequestToSellCryptocurrency.getInstance().sellCryptocurrency(selectedCryptocurrency,selectedCurrency,Double.parseDouble(tfCryptocurrencyAmount.getText()),Double.parseDouble(tfCurrencyAmount.getText()));
        showInfo("Sold successfully");
    }

    private void setComboBoxItems() {
        ObservableList<String> currency = FXCollections.observableArrayList("PLN", "USD", "EUR");
        cbxCurrency.setItems(currency);
        cbxCurrency.getSelectionModel().selectFirst();
        ObservableList<String> cryptocurrency = FXCollections.observableArrayList("BTC", "ETH", "DOGE");
        cbxCryptocurrency.setItems(cryptocurrency);
        cbxCryptocurrency.getSelectionModel().selectFirst();

    }

    private MainStage getMainStage() {
        return MainStage.getInstance();
    }


    private void showInfo(String info)
    {
        lbInfo.setText(info);
    }

    private void resetLabel()
    {
        lbInfo.setText("");
    }

    private void setUI()
    {
        tfCurrentCryptocurrencyRates.setText("1 "+ selectedCryptocurrency);
        tfCurrentCurrencyRates.setText(CryptocurrencyExchangeRates.getInstance().getExchangeRatesMap().get(selectedCryptocurrency).get(selectedCurrencyIndex).toString()+" "+selectedCurrency);
    }

    @FXML
    void tfCryptocurrencyAmountOnKeyTyped(KeyEvent event) {
        showAmountUserCanGetFromSelling();
    }

    private void showAmountUserCanGetFromSelling()
    {
        if(sellValidation.checkIfDouble(tfCryptocurrencyAmount.getText()).equals(Valid.VALID)) {
            tfCurrencyAmount.setText(String.valueOf(sellValidation.getRoundedCurrency(Double.parseDouble(tfCryptocurrencyAmount.getText())*CryptocurrencyExchangeRates.getInstance().getExchangeRatesMap().get(selectedCryptocurrency).get(selectedCurrencyIndex))));
        }
    }


    private void updateRates()
    {
        CryptocurrencyExchangeRates.getInstance().updateRates(selectedCryptocurrency,false);
    }



}
