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
import validation.Buy_Send_Sell_ExchangeViewsValidation;


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
    private Label lbCurrencyError;

    @FXML
    private Label lbCryptocurrencyError;
    //endregion

    private static double TAX = 0.02;
    private int numberOfErrors;
    private int selectedCurrencyIndex=0;
    private String selectedCryptocurrency="BTC";
    private String selectedCurrency="PLN";
    private double amountUserCanBuy;
    Buy_Send_Sell_ExchangeViewsValidation buyValidation = Buy_Send_Sell_ExchangeViewsValidation.getInstance();

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
        numberOfErrors=0;
        resetLabels();
        checkIfDouble();
        checkIfSufficientFunds();
        if(numberOfErrors==0)
        {
            buyCryptocurrency();
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
        showInfo();
    }

    private void checkIfDouble()
    {
        String currencyValid = buyValidation.checkIfDouble(tfCurrencyAmount.getText());
        String cryptocurrencyValid = buyValidation.checkIfDouble(tfCryptocurrencyAmount.getText());

        if(!currencyValid.equals("valid"))
        {
            showError(lbCurrencyError,currencyValid);
            numberOfErrors++;
        }

        if(!cryptocurrencyValid.equals("valid"))
        {
            showError(lbCryptocurrencyError,cryptocurrencyValid);
            numberOfErrors++;
        }
    }

    private void checkIfSufficientFunds()
    {
        String fundsValid = buyValidation.checkIfSufficientFundsToBuy(selectedCurrency,Double.parseDouble(tfCurrencyAmount.getText()),Double.parseDouble(tfCryptocurrencyAmount.getText()),CryptocurrencyExchangeRates.getInstance().getExchangeRatesMap().get(selectedCryptocurrency).get(selectedCurrencyIndex));
        if(!fundsValid.equals("valid"))
        {
            showError(lbCurrencyError,fundsValid);
            numberOfErrors++;
        }
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
       lbCryptocurrencyError.setText("");
       lbCurrencyError.setText("");
   }

   private void showError(Label errorLabel, String error)
   {
       errorLabel.setText(error);
   }

   private void showAmountUserCanBuy()
   {
       if(buyValidation.checkIfDouble(tfCurrencyAmount.getText()).equals("valid")) {

           double amountUserCanBuy = Double.parseDouble(tfCurrencyAmount.getText()) / CryptocurrencyExchangeRates.getInstance().getExchangeRatesMap().get(selectedCryptocurrency).get(selectedCurrencyIndex);
           amountUserCanBuy -= amountUserCanBuy*TAX;
           tfCryptocurrencyAmount.setText(String.valueOf(amountUserCanBuy));
       }
   }



    private void showInfo()
    {
        lbCurrencyError.setText("Successfully bought");
    }

    private MainStage getMainStage() {
        return MainStage.getInstance();
    }
}
