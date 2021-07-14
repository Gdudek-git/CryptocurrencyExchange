package controller;

import model.cryptocurrency.CryptocurrencyExchangeRatesModel;
import model.database.DatabaseConnectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.hibernate.Session;
import model.operations.SellCryptocurrencyModel;
import model.session.ChangeUserDataModel;
import model.validation.SellViewValidationModel;
import model.validation.Valid;

public class SellViewController {

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

    private DatabaseConnectionModel databaseConnectionModel;
    private CryptocurrencyExchangeRatesModel cryptocurrencyExchangeRatesModel;
    private SellCryptocurrencyModel sellCryptocurrencyModel;
    private ChangeUserDataModel changeUserDataModel;
    private SellViewValidationModel sellViewValidationModel;

    private int selectedCurrencyIndex;
    private String selectedCryptocurrency;
    private String selectedCurrency;
    private Session session;

    @FXML
    public void initialize() {

        databaseConnectionModel = new DatabaseConnectionModel();
        cryptocurrencyExchangeRatesModel = new CryptocurrencyExchangeRatesModel();
        sellCryptocurrencyModel = new SellCryptocurrencyModel();
        changeUserDataModel = new ChangeUserDataModel();
        sellViewValidationModel = new SellViewValidationModel();

        selectedCryptocurrency = "BTC";
        selectedCurrency = "PLN";
        selectedCurrencyIndex=0;

        establishConnectionWithDatabase();
        setComboBoxItems();
        updateRates();
        setUI();
    }
    private void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> session =  databaseConnectionModel.getSessionObj());
        thread.start();
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
        resetLabel();
        if(checkIfDouble())
        {
            if(checkIfSufficientFunds()) {
                sellCryptocurrency();
            }
        }
    }

    private void resetLabel()
    {
        lbInfo.setText("");
    }

    public boolean checkIfDouble()
    {
        String isDouble = sellViewValidationModel.checkIfDouble(tfCryptocurrencyAmount.getText());
        if(!isDouble.equals(Valid.VALID))
        {
            showInfo(isDouble);
            return false;
        }
        return true;
    }

    private boolean checkIfSufficientFunds()
    {
        String sufficientFunds = sellViewValidationModel.checkIfSufficientFundsToSell(selectedCryptocurrency,tfCryptocurrencyAmount.getText());
        if(!sufficientFunds.equals(Valid.VALID))
        {
            showInfo(sufficientFunds);
            return false;
        }
        return true;
    }

    private void sellCryptocurrency()
    {   sellCryptocurrencyModel.sellCryptocurrency(selectedCryptocurrency,selectedCurrency,tfCryptocurrencyAmount.getText(),tfCurrencyAmount.getText());
        changeUserDataModel.updateLoggedUserData(session);
        showInfo("Sold successfully");
    }

    private void showInfo(String info)
    {
        lbInfo.setText(info);
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
        databaseConnectionModel.closeConnection(session);
        View.getInstance().setView(View.getView("UserView.fxml"));
    }

}
