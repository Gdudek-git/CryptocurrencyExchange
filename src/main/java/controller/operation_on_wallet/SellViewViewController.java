package controller.operation_on_wallet;

import view.AvailableViews;
import view.View;
import model.cryptocurrency.CryptocurrencyExchangeRatesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.operations.SellCryptocurrencyModel;
import model.validation.SellViewValidationModel;
import model.validation.Valid;

public class SellViewViewController extends AbstractOperationOnWalletViewController {

    //region Controls

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

    private CryptocurrencyExchangeRatesModel cryptocurrencyExchangeRatesModel;
    private SellCryptocurrencyModel sellCryptocurrencyModel;
    private SellViewValidationModel sellViewValidationModel;

    private int selectedCurrencyIndex;
    private String selectedCryptocurrency;
    private String selectedCurrency;

    @FXML
    public void initialize() {

        cryptocurrencyExchangeRatesModel = new CryptocurrencyExchangeRatesModel();
        sellCryptocurrencyModel = new SellCryptocurrencyModel();
        sellViewValidationModel = new SellViewValidationModel();

        selectedCryptocurrency = "BTC";
        selectedCurrency = "PLN";
        selectedCurrencyIndex=0;

        super.establishConnectionWithDatabase();
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

    private void updateRates()
    {
        cryptocurrencyExchangeRatesModel.updateRates(selectedCryptocurrency,false);
    }

    private void setUI()
    {  tfCurrentCryptocurrencyRates.setText("1 "+ selectedCryptocurrency);
        tfCurrentCurrencyRates.setText(cryptocurrencyExchangeRatesModel.getExchangeRatesMap().get(selectedCryptocurrency).get(selectedCurrencyIndex).toString()+" "+selectedCurrency);
    }

    @FXML
    private void cbxCurrencyOnAction(ActionEvent event) {
        selectedCurrency=cbxCurrency.getSelectionModel().getSelectedItem();
        selectedCurrencyIndex = cbxCurrency.getSelectionModel().getSelectedIndex();

        setUI();
        showAmountUserCanGetFromSelling();
    }

    @FXML
    private void cbxCryptocurrencyOnAction(ActionEvent event) {
        selectedCryptocurrency = cbxCryptocurrency.getSelectionModel().getSelectedItem();
        updateRates();
        setUI();
        showAmountUserCanGetFromSelling();
    }

    @FXML
    private void btnSellOnAction(ActionEvent event) {
        super.resetLabel(lbInfo);
        if(checkIfDouble())
        {
            if(checkIfSufficientFunds()) {
                sellCryptocurrency();
            }
        }
    }

    public boolean checkIfDouble()
    {
        String isDouble = sellViewValidationModel.checkIfDouble(tfCryptocurrencyAmount.getText());
        if(!isDouble.equals(Valid.VALID))
        {
            super.showInfo(lbInfo,isDouble);
            return false;
        }
        return true;
    }

    protected boolean checkIfSufficientFunds()
    {
        String sufficientFunds = sellViewValidationModel.checkIfSufficientFundsToSell(selectedCryptocurrency,tfCryptocurrencyAmount.getText());
        if(!sufficientFunds.equals(Valid.VALID))
        {
            super.showInfo(lbInfo,sufficientFunds);
            return false;
        }
        return true;
    }

    private void sellCryptocurrency()
    {   sellCryptocurrencyModel.sellCryptocurrency(selectedCryptocurrency,selectedCurrency,tfCryptocurrencyAmount.getText(),tfCurrencyAmount.getText());
        super.updateLoggedUserData();
        super.showInfo(lbInfo,"Sold successfully");
    }

    @FXML
    private void tfCryptocurrencyAmountOnKeyTyped(KeyEvent event) {
        showAmountUserCanGetFromSelling();
    }

    private void showAmountUserCanGetFromSelling()
    {
        if(sellViewValidationModel.checkIfDouble(tfCryptocurrencyAmount.getText()).equals(Valid.VALID)) {
            tfCurrencyAmount.setText(String.valueOf(sellViewValidationModel.getRoundedCurrencyExchangeRate(tfCryptocurrencyAmount.getText(), cryptocurrencyExchangeRatesModel.getExchangeRatesMap().get(selectedCryptocurrency).get(selectedCurrencyIndex))));
        }
    }

    @FXML
    private void btnReturnOnClick(ActionEvent event)
    {
        super.closeConnection();
        View.getInstance().setView(View.getView(AvailableViews.MAIN_MENU_VIEW));
    }

}
