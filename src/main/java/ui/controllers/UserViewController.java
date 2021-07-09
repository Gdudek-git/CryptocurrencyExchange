package ui.controllers;

import cryptocurrency.CryptocurrencyExchangeRates;
import cryptocurrency.CryptocurrencyRatesChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import services.CryptocurrencyRatesUpdateTimer;
import session.LoggedUser;

import static ui.controllers.MainStage.getScene;

public class UserViewController {

    //region Controls
    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfBalance;

    @FXML
    private TextField tfDeposit;

    @FXML
    private MenuItem miPLN;

    @FXML
    private MenuItem miUSD;

    @FXML
    private MenuItem miEUR;

    @FXML
    private MenuItem miBTC;

    @FXML
    private MenuItem miDOGE;


    @FXML
    private MenuItem mi10s;

    @FXML
    private MenuItem mi1Min;

    @FXML
    private RadioButton rbHourly;

    @FXML
    private RadioButton rbDaily;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<?, ?> cryptocurrencyChart;


    @FXML
    private TextField tfCurrencyType;

    @FXML
    private TextField tfCryptocurrencyRate;

    @FXML
    private TextField tfAverageCryptocurrencyRate;
    //endregion


    private String selectedCryptocurrency = "BTC";
    private String selectedCurrency = "PLN";
    private int selectedCurrencyIndex = 0;
    private int selectedUpdateRate=10000;
    private String chartType = "minutes";


    @FXML
    void initialize()
    {
        CryptocurrencyRatesUpdateTimer.getInstance().setUserViewController(this);
        tfUsername.setText(LoggedUser.getInstance().getLoggedUser().getUsername());
        updateTextFieldBalance();
        updateTextFieldDeposit();
        updateRateAfterChangingCurrency_Cryptocurrency();
        updateCryptocurrencyRatesUI();
        updateChart();
        callUpdateTimer();
    }


    public void updateChart()
    {

        CryptocurrencyRatesChart.getInstance().createChart(cryptocurrencyChart,selectedCryptocurrency,selectedCurrency,chartType,y);
    }


    private void callUpdateTimer()
    {
        CryptocurrencyRatesUpdateTimer.getInstance().stopTimer();
        CryptocurrencyRatesUpdateTimer.getInstance().startUpdating(selectedUpdateRate,selectedCryptocurrency);
    }

    private void updateRateAfterChangingCurrency_Cryptocurrency()
    {
        CryptocurrencyExchangeRates.getInstance().updateRates(selectedCryptocurrency,false);
        CryptocurrencyExchangeRates.getInstance().updateRates(selectedCryptocurrency,true);
    }


    private void updateTextFieldBalance()
    {
        switch(selectedCurrency)
        {
            case "PLN":tfBalance.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getPln()));break;
            case "EUR":tfBalance.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getEur()));break;
            case "USD":tfBalance.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getUsd()));break;
        }
        tfBalance.setText(tfBalance.getText()+selectedCurrency);
    }

    private void updateTextFieldDeposit()
    {
        switch(selectedCryptocurrency)
        {
            case "BTC":tfDeposit.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getBtc()));break;
            case "DOGE":tfDeposit.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getDoge()));break;
            case "ETH":tfDeposit.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getEth()));break;
        }
        tfDeposit.setText(tfDeposit.getText()+selectedCryptocurrency);
    }

    public synchronized void updateCryptocurrencyRatesUI()
    {
        tfCurrencyType.setText(selectedCryptocurrency);
        tfAverageCryptocurrencyRate.setText(CryptocurrencyExchangeRates.getInstance().getExchangeRatesMap().get("AVERAGE").get(selectedCurrencyIndex).toString()+selectedCurrency);
        tfCryptocurrencyRate.setText(CryptocurrencyExchangeRates.getInstance().getExchangeRatesMap().get(selectedCryptocurrency).get(selectedCurrencyIndex).toString()+selectedCurrency);
    }


    @FXML
    void currencyMenuOnAction(ActionEvent event) {

           if(event.getSource().equals(miPLN))
           {
               selectedCurrencyIndex=0;
               selectedCurrency="PLN";
           }
           else if(event.getSource().equals(miEUR))
           {
               selectedCurrencyIndex=1;
               selectedCurrency="EUR";
           }
           else if(event.getSource().equals(miUSD))
           {
               selectedCurrencyIndex=2;
               selectedCurrency="USD";
           }
           updateRateAfterChangingCurrency_Cryptocurrency();
           updateCryptocurrencyRatesUI();
           updateTextFieldBalance();
           updateChart();

    }

    @FXML
    void cryptocurrencyMenuOnAction(ActionEvent event) {

        if(event.getSource().equals(miBTC))
        {
            selectedCryptocurrency="BTC";
        }
        else if(event.getSource().equals(miDOGE))
        {
            selectedCryptocurrency="DOGE";
        }
        else
        {
            selectedCryptocurrency="ETH";
        }
        updateRateAfterChangingCurrency_Cryptocurrency();
        updateCryptocurrencyRatesUI();
        updateTextFieldDeposit();
        updateChart();
    }

    @FXML
    void updateMenuOnAction(ActionEvent event) {

        if(event.getSource().equals(mi10s))
        {
            selectedUpdateRate = 10000;
        }
        else if(event.getSource().equals(mi1Min))
        {
            selectedUpdateRate=60000;
        }
        else
        {
            selectedUpdateRate=600000;
        }
        callUpdateTimer();
    }


    @FXML
    void rbOnAction(ActionEvent event) {

        if(event.getSource().equals(rbHourly))
        {
            chartType="hours";
        }
        else if(event.getSource().equals(rbDaily)) {
            chartType="days";
        }
        else
        {
            chartType="minutes";
        }
        updateChart();
    }

    @FXML
    void btnBuyOnAction(ActionEvent event) {
        stopTimer();
        getMainStage().setScene(getScene("BuyView.fxml"));
    }

    @FXML
    void btnExchangeOnAction(ActionEvent event) {
        stopTimer();
        getMainStage().setScene(getScene("ExchangeView.fxml"));
    }

    @FXML
    void btnSellOnAction(ActionEvent event) {stopTimer();
        getMainStage().setScene(getScene("SellView.fxml"));
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {stopTimer();
        getMainStage().setScene(getScene("SendView.fxml"));
    }

    @FXML
    void btnAccountOnAction(ActionEvent event) {
        stopTimer();
        getMainStage().setScene(getScene("AccountView.fxml"));
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {stopTimer();
        getMainStage().setScene(getScene("LoginView.fxml"));
    }


    private void stopTimer()
    {
        CryptocurrencyRatesUpdateTimer.getInstance().stopTimer();
    }

    private MainStage getMainStage() {
        return MainStage.getInstance();
    }

}