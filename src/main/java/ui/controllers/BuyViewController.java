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
import services.RequestToBuyCryptocurrency;
import validation.BuyViewValidation;


import static ui.controllers.MainStage.getScene;

public class BuyViewController {

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
    private TextField tfCurrentCurrencyRates;

    @FXML
    private TextField tfCurrentCryptocurrencyRates;

    @FXML
    private Label lbInfo;


    //endregion

    private int selectedCurrencyIndex=0;
    private String selectedCryptocurrency="BTC";
    private String selectedCurrency="PLN";
    BuyViewValidation buyValidation = BuyViewValidation.getInstance();

    @FXML
    public void initialize() {
        RequestToBuyCryptocurrency.getInstance().establishConnectionWithDatabase();
        setComboBoxItems();
        updateRates();
        setUI();
    }

    private void setComboBoxItems() {
        ObservableList<String> currency = FXCollections.observableArrayList("PLN", "USD", "EUR");
        cbxCurrency.setItems(currency);
        cbxCurrency.getSelectionModel().selectFirst();
        ObservableList<String> cryptocurrency = FXCollections.observableArrayList("BTC", "ETH", "DOGE");
        cbxCryptocurrency.setItems(cryptocurrency);
        cbxCryptocurrency.getSelectionModel().selectFirst();

    }

    @FXML
    void cbxCurrencyOnAction(ActionEvent event)
    {
        switch(cbxCurrency.getSelectionModel().getSelectedItem()){
            case "PLN":selectedCurrencyIndex=0;break;
            case "EUR":selectedCurrencyIndex=1;break;
            case "USD":selectedCurrencyIndex=2;break;
        }
        selectedCurrency=cbxCurrency.getSelectionModel().getSelectedItem();
        setUI();
        showAmountUserCanBuy();
    }

    @FXML
    void cbxCryptocurrencyOnAction(ActionEvent event)
    {
        selectedCryptocurrency = cbxCryptocurrency.getSelectionModel().getSelectedItem();
        updateRates();
        setUI();
        showAmountUserCanBuy();
    }


    @FXML
    void btnBuyOnAction(ActionEvent event) {

        resetLabels();

        if(checkIfDouble()) {
            if(checkIfSufficientFunds())
            {
                buyCryptocurrency();
            }
        }
    }


    @FXML
    void btnReturnOnClick(ActionEvent event) {
        RequestToBuyCryptocurrency.getInstance().closeConnectionWithDatabase();
        getMainStage().setScene(getScene("UserView.fxml"));
    }

    @FXML
    void tfOnKeyTyped(KeyEvent event) {
        showAmountUserCanBuy();
    }


    private void buyCryptocurrency()
    {
        RequestToBuyCryptocurrency.getInstance().buyCryptocurrency(selectedCurrency,selectedCryptocurrency,Double.parseDouble(tfCurrencyAmount.getText()),Double.parseDouble(tfCryptocurrencyAmount.getText()));
        showInfo("Bought successfully");
    }

    private boolean checkIfDouble()
    {
        String currencyValid = buyValidation.checkIfDouble(tfCurrencyAmount.getText());

        if(!currencyValid.equals("valid"))
        {
            showInfo(currencyValid);
            return false;
        }

        return true;
    }

    private boolean checkIfSufficientFunds()
    {
        String fundsValid = buyValidation.checkIfSufficientFundsToBuy(selectedCurrency,tfCryptocurrencyAmount.getText());
        if(!fundsValid.equals("valid"))
        {
            showInfo(fundsValid);
            return false;
        }
        return true;
    }

    private void setUI()
    {
        tfCurrentCryptocurrencyRates.setText("1 "+ selectedCryptocurrency);
        tfCurrentCurrencyRates.setText(CryptocurrencyExchangeRates.getInstance().getExchangeRatesMap().get(selectedCryptocurrency).get(selectedCurrencyIndex).toString()+" "+selectedCurrency);
    }

   private void updateRates()
   {
       CryptocurrencyExchangeRates.getInstance().updateRates(selectedCryptocurrency);
   }


   private void resetLabels()
   {
       lbInfo.setText("");
   }



   private void showAmountUserCanBuy()
   {
       if(buyValidation.checkIfDouble(tfCurrencyAmount.getText()).equals("valid")) {

           tfCryptocurrencyAmount.setText(String.valueOf(Double.parseDouble(tfCurrencyAmount.getText()) / CryptocurrencyExchangeRates.getInstance().getExchangeRatesMap().get(selectedCryptocurrency).get(selectedCurrencyIndex)));
       }
   }

    private void showInfo(String info)
    {
        lbInfo.setText(info);
    }

    private MainStage getMainStage() {
        return MainStage.getInstance();
    }
}
