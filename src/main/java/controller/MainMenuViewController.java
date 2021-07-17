package controller;

import model.cryptocurrency.CryptocurrencyExchangeRatesModel;
import model.cryptocurrency.CryptocurrencyRatesChartModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import model.TimerModel;
import model.session.LoggedUser;

import java.util.stream.IntStream;

public class MainMenuViewController {

    //region Controls
    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfBalance;

    @FXML
    private TextField tfDeposit;

    @FXML
    private Menu currencyMenu;

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


    private CryptocurrencyExchangeRatesModel cryptocurrencyExchangeRatesModel;
    private CryptocurrencyRatesChartModel cryptocurrencyRatesChartModel;
    private TimerModel timerModel;

    private String selectedCryptocurrency;
    private String selectedCurrency;
    private String chartType;

    private int selectedCurrencyIndex;
    private int updateRate;


    @FXML
    void initialize()
    {
        cryptocurrencyExchangeRatesModel = new CryptocurrencyExchangeRatesModel();
        cryptocurrencyRatesChartModel = new CryptocurrencyRatesChartModel();
        timerModel = new TimerModel();
        timerModel.setMainMenuViewController(this);
        tfUsername.setText(LoggedUser.getInstance().getLoggedUser().getUsername());
        setVariables();
        setTextFieldBalance();
        setTextFieldDeposit();
        updateRateAfterChangingCurrency_Cryptocurrency();
        setCryptocurrencyRatesUI();
        setChart();
        startTimer();
    }

    private void setVariables()
    {
        selectedCryptocurrency="BTC";
        selectedCurrency = "PLN";
        selectedCurrencyIndex = 0;
        updateRate = 10000;
        chartType="minutes";
    }

    private void setTextFieldBalance()
    {
        switch(selectedCurrency)
        {
            case "PLN":tfBalance.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getPln()));break;
            case "EUR":tfBalance.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getEur()));break;
            case "USD":tfBalance.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getUsd()));break;
        }
        tfBalance.setText(tfBalance.getText()+selectedCurrency);
    }

    private void setTextFieldDeposit()
    {
        switch(selectedCryptocurrency)
        {
            case "BTC":tfDeposit.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getBtc()));break;
            case "DOGE":tfDeposit.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getDoge()));break;
            case "ETH":tfDeposit.setText(String.valueOf(LoggedUser.getInstance().getLoggedUser().getUserWallet().getEth()));break;
        }
        tfDeposit.setText(tfDeposit.getText()+selectedCryptocurrency);
    }

    private void updateRateAfterChangingCurrency_Cryptocurrency()
    {
        cryptocurrencyExchangeRatesModel.updateRates(selectedCryptocurrency,false);
        cryptocurrencyExchangeRatesModel.updateRates(selectedCryptocurrency,true);
    }

    public synchronized void setCryptocurrencyRatesUI()
    {
        tfCurrencyType.setText(selectedCryptocurrency);
        tfAverageCryptocurrencyRate.setText(cryptocurrencyExchangeRatesModel.getExchangeRatesMap().get("AVERAGE").get(selectedCurrencyIndex).toString()+selectedCurrency);
        tfCryptocurrencyRate.setText(cryptocurrencyExchangeRatesModel.getExchangeRatesMap().get(selectedCryptocurrency).get(selectedCurrencyIndex).toString()+selectedCurrency);
    }

    public void setChart()
    {
        cryptocurrencyRatesChartModel.createChart(cryptocurrencyChart,selectedCryptocurrency,selectedCurrency,chartType,y);
    }

    private void startTimer()
    {
        timerModel.stopTimer();
        timerModel.startUpdating(updateRate);
    }

    public void updateRates()
    {
        cryptocurrencyExchangeRatesModel.updateRates(selectedCryptocurrency,false);
        cryptocurrencyExchangeRatesModel.updateRates(selectedCryptocurrency,true);
    }

    @FXML
    void currencyMenuOnAction(ActionEvent event) {

        MenuItem currencyType = (MenuItem) event.getSource();
        selectedCurrency = currencyType.getText();
        selectedCurrencyIndex = IntStream.range(0,currencyMenu.getItems().size())
                .filter(item -> currencyMenu.getItems().get(item).getText().equals(selectedCurrency)).findFirst().getAsInt();

        updateRateAfterChangingCurrency_Cryptocurrency();
        setCryptocurrencyRatesUI();
        setTextFieldBalance();
        setChart();

    }

    @FXML
    void cryptocurrencyMenuOnAction(ActionEvent event) {
        MenuItem cryptocurrencyType = (MenuItem) event.getSource();
        selectedCryptocurrency = cryptocurrencyType.getText();

        updateRateAfterChangingCurrency_Cryptocurrency();
        setCryptocurrencyRatesUI();
        setTextFieldDeposit();
        setChart();
    }

    @FXML
    void updateMenuOnAction(ActionEvent event) {
        MenuItem menuItem  = (MenuItem)event.getSource();
        updateRate = timerModel.changeUpdateRate(menuItem.getText());
        startTimer();
    }


    @FXML
    void rbOnAction(ActionEvent event) {
        RadioButton radioButton =(RadioButton) event.getSource();
        chartType =  cryptocurrencyRatesChartModel.changeChartType(radioButton.getText());
        setChart();
    }

    @FXML
    void btnBuyOnAction(ActionEvent event) {
        stopTimer();
        View.getInstance().setView(View.getView("BuyView.fxml"));
    }

    @FXML
    void btnExchangeOnAction(ActionEvent event) {
        stopTimer();
        View.getInstance().setView(View.getView("ExchangeView.fxml"));
    }

    @FXML
    void btnSellOnAction(ActionEvent event) {
        stopTimer();
        View.getInstance().setView(View.getView("SellView.fxml"));
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        stopTimer();
        View.getInstance().setView(View.getView("SendView.fxml"));
    }

    @FXML
    void btnAccountOnAction(ActionEvent event) {
        stopTimer();
        View.getInstance().setView(View.getView("AccountView.fxml"));
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        stopTimer();
        View.getInstance().setView(View.getView("LoginView.fxml"));
    }


    private void stopTimer()
    {
        timerModel.stopTimer();
    }


}