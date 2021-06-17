package ui.controllers;

import currency.CurrencyExchangeRates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import services.RequestToExchangeCurrency;
import validation.ExchangeViewValidation;

import static ui.controllers.MainStage.getScene;

public class ExchangeViewController {


    //region Controllers
    @FXML
    private ComboBox<String> cbxCurrencyToObtain;

    @FXML
    private ComboBox<String> cbxCurrentCurrency;

    @FXML
    private TextField tfCurrencyAmount;

    @FXML
    private TextField tfCurrencyToObtainAmount;

    @FXML
    private TextField tfCurrentCurrencyRate;

    @FXML
    private TextField tfCurrencyToObtainRate;

    @FXML
    private Label lbInfo;
    //endregion
    private static final String VALID = "valid";
    private String selectedCurrency="PLN";
    private String selectedCurrencyToObtain="EUR";
    ExchangeViewValidation exchangeValidation = ExchangeViewValidation.getInstance();

    @FXML
    public void initialize()
    {
        RequestToExchangeCurrency.getInstance().establishConnectionWithDatabase();
        updateRates();
        setUI();
        setComboBoxItems();
    }

    @FXML
    void btnExchangeOnAction(ActionEvent event) {
        resetLabel();
        if(checkIfDouble()){
            if (checkIfSufficientFunds()) {
                exchangeCurrency();
            }
        }
    }


    private boolean checkIfDouble()
    {
        if(!exchangeValidation.checkIfDouble(tfCurrencyAmount.getText()).equals(VALID))
        {
            showInfo(exchangeValidation.checkIfDouble(tfCurrencyAmount.getText()));
            return false;
        }
        return true;

    }

    private boolean checkIfSufficientFunds()
    {
        if(!exchangeValidation.checkIfSufficientFundsToExchange(selectedCurrency,tfCurrencyAmount.getText()).equals(VALID))
        {
            showInfo(exchangeValidation.checkIfSufficientFundsToExchange(selectedCurrency,tfCurrencyAmount.getText()));
            return false;
        }
        return true;
    }

    private void exchangeCurrency()
    {
        RequestToExchangeCurrency.getInstance().exchangeCurrency(selectedCurrency,selectedCurrencyToObtain,Double.parseDouble(tfCurrencyAmount.getText()),Double.parseDouble(tfCurrencyToObtainAmount.getText()));
        showInfo("Exchanged successfully");
    }



    @FXML
    void btnReturnOnAction(ActionEvent event) {
        RequestToExchangeCurrency.getInstance().closeConnectionWithDatabase();
        getMainStage().setScene(getScene("UserView.fxml"));
    }

    private void setComboBoxItems() {
        ObservableList<String> currency = FXCollections.observableArrayList("PLN", "EUR", "USD");
        ObservableList<String> currencyToObtain = FXCollections.observableArrayList( "EUR","USD");
        cbxCurrentCurrency.setItems(currency);
        cbxCurrentCurrency.getSelectionModel().selectFirst();
        cbxCurrencyToObtain.setItems(currencyToObtain);
        cbxCurrencyToObtain.getSelectionModel().selectFirst();
    }

    @FXML
    void cbxCurrencyToObtainOnAction(ActionEvent event){
        if(cbxCurrencyToObtain.getItems().size()>1) {
            selectedCurrencyToObtain = cbxCurrencyToObtain.getSelectionModel().getSelectedItem();
            if(selectedCurrencyToObtain==null)
            {
                selectedCurrencyToObtain="EUR";
            }
            setUI();
            showAmountUserCanBuy();
        }
    }
    @FXML
    void cbxCurrentCurrencyOnAction(ActionEvent event) {
        selectedCurrency = cbxCurrentCurrency.getSelectionModel().getSelectedItem();
        exchangeValidation.validateComboBox(cbxCurrencyToObtain,selectedCurrency);
        selectedCurrencyToObtain = cbxCurrencyToObtain.getSelectionModel().getSelectedItem();
        updateRates();
        setUI();
        showAmountUserCanBuy();
    }


    @FXML
    void tfCurrencyAmountOnKeyTyped(KeyEvent event) {
        showAmountUserCanBuy();
    }

    private void setUI()
    {
        tfCurrentCurrencyRate.setText("1 "+selectedCurrency);
        if(selectedCurrency.equals("PLN")) {
            tfCurrencyToObtainRate.setText(exchangeValidation.getRoundedCurrency(CurrencyExchangeRates.getInstance().getExchangeRates().get(selectedCurrency + "To" + selectedCurrencyToObtain).get(1))+" "+selectedCurrencyToObtain);
        }else{
            tfCurrencyToObtainRate.setText(exchangeValidation.getRoundedCurrency(CurrencyExchangeRates.getInstance().getExchangeRates().get(selectedCurrency + "To" + selectedCurrencyToObtain).get(0))+" "+selectedCurrencyToObtain);
        }
    }

    private void updateRates()
    {
        CurrencyExchangeRates.getInstance().updateRates();
    }

    private void showAmountUserCanBuy()
    {
        if(exchangeValidation.checkIfDouble(tfCurrencyAmount.getText()).equals(VALID)) {
            if(selectedCurrency.equals("PLN")) {
                tfCurrencyToObtainAmount.setText(String.valueOf(exchangeValidation.getRoundedCurrency(Double.parseDouble(tfCurrencyAmount.getText()) * CurrencyExchangeRates.getInstance().getExchangeRates().get(selectedCurrency + "To" + selectedCurrencyToObtain).get(1))));
            }else
            {
                tfCurrencyToObtainAmount.setText(String.valueOf(exchangeValidation.getRoundedCurrency(Double.parseDouble(tfCurrencyAmount.getText()) * CurrencyExchangeRates.getInstance().getExchangeRates().get(selectedCurrency + "To" + selectedCurrencyToObtain).get(0))));
            }
        }
    }

    private void showInfo(String info)
    {
        lbInfo.setText(info);
    }

    private void resetLabel()
    {
        lbInfo.setText("");
    }

    private MainStage getMainStage() {
        return MainStage.getInstance();
    }

}
