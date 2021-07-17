package controller.operation_on_wallet;

import controller.View;
import model.cryptocurrency.CryptocurrencyExchangeRatesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.operations.BuyCryptocurrencyModel;
import model.validation.BuyViewValidationModel;
import model.validation.Valid;

public class BuyViewViewController extends AbstractOperationOnWalletViewController {

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
    private TextField tfCurrentCurrencyRates;

    @FXML
    private TextField tfCurrentCryptocurrencyRates;

    @FXML
    private Label lbInfo;


    //endregion

    private CryptocurrencyExchangeRatesModel cryptocurrencyExchangeRatesModel;
    private BuyCryptocurrencyModel buyCryptocurrencyModel;
    private BuyViewValidationModel buyViewValidationModel;

    private int selectedCurrencyIndex;
    private String selectedCryptocurrency;
    private String selectedCurrency;

    @FXML
    public void initialize() {
        cryptocurrencyExchangeRatesModel = new CryptocurrencyExchangeRatesModel();
        buyCryptocurrencyModel = new BuyCryptocurrencyModel();
        buyViewValidationModel = new BuyViewValidationModel();

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
    {
        tfCurrentCryptocurrencyRates.setText("1 "+ selectedCryptocurrency);
        tfCurrentCurrencyRates.setText(cryptocurrencyExchangeRatesModel.getExchangeRatesMap()
                .get(selectedCryptocurrency).get(selectedCurrencyIndex)
                .toString()+" "+selectedCurrency);
    }

    @FXML
    private void cbxCurrencyOnAction(ActionEvent event)
    {
        selectedCurrencyIndex =cbxCurrency.getSelectionModel().getSelectedIndex();
        selectedCurrency=cbxCurrency.getSelectionModel().getSelectedItem();
        setUI();
        showAmountUserCanBuy();
    }

    @FXML
    private void cbxCryptocurrencyOnAction(ActionEvent event)
    {
        selectedCryptocurrency = cbxCryptocurrency.getSelectionModel().getSelectedItem();
        updateRates();
        setUI();
        showAmountUserCanBuy();
    }

    @FXML
    private void btnBuyOnAction(ActionEvent event) {
        super.resetLabel(lbInfo);
        if(checkIfDouble()) {
            if(checkIfSufficientFunds())
            {
                buyCryptocurrency();
            }
        }
    }

    private boolean checkIfDouble()
    {
        String isDouble = buyViewValidationModel.checkIfDouble(tfCurrencyAmount.getText());
        if(!isDouble.equals(Valid.VALID))
        {
            super.showInfo(lbInfo,isDouble);
            return false;
        }
        return true;
    }

    protected boolean checkIfSufficientFunds()
    {
        String sufficientFunds = buyViewValidationModel.checkIfSufficientFundsToBuy(selectedCurrency,tfCurrencyAmount.getText());
        if(!sufficientFunds.equals(Valid.VALID))
        {
            super.showInfo(lbInfo,sufficientFunds);
            return false;
        }
        return true;
    }

    private void buyCryptocurrency()
    {
        buyCryptocurrencyModel.buyCryptocurrency(selectedCurrency,selectedCryptocurrency,tfCurrencyAmount.getText(),tfCryptocurrencyAmount.getText());
        super.updateLoggedUserData();
        super.showInfo(lbInfo,"Bought successfully");
    }

    @FXML
    private void tfOnKeyTyped(KeyEvent event) {
        showAmountUserCanBuy();
    }

    private void showAmountUserCanBuy()
    {
        if(buyViewValidationModel.checkIfDouble(tfCurrencyAmount.getText()).equals(Valid.VALID)) {

            tfCryptocurrencyAmount.setText(String.valueOf(Double.parseDouble(tfCurrencyAmount.getText())
                    /cryptocurrencyExchangeRatesModel.getExchangeRatesMap()
                    .get(selectedCryptocurrency).get(selectedCurrencyIndex)));
        }
    }

    @FXML
    void btnReturnOnClick(ActionEvent event) {
        super.closeConnection();
        View.getInstance().setView(View.getView("MainMenuView.fxml"));
    }
}
