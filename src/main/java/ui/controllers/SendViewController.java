package ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.RequestToSendCryptocurrency;
import validation.SendViewValidation;
import validation.Valid;

import static ui.controllers.MainStage.getScene;

public class SendViewController {

    //region Controls
    @FXML
    private TextField tfCryptocurrencyAmount;

    @FXML
    private TextField tfRecipientNickname;

    @FXML
    private ComboBox<String> cbxCryptocurrency;

    @FXML
    private Label lbInfo;
    //endregion

    private String selectedCryptocurrency="BTC";
    private SendViewValidation sendValidation = SendViewValidation.getInstance();

    @FXML
    public void initialize() {
        sendValidation.establishConnectionWithDatabase();
        RequestToSendCryptocurrency.getInstance().establishConnectionWithDatabase();
        setComboBoxItems();
    }

    @FXML
    void btnReturnOnAction(ActionEvent event)
    {
        RequestToSendCryptocurrency.getInstance().closeConnectionWithDatabase();
        sendValidation.closeConnectionWithDatabase();
        getMainStage().setScene(getScene("UserView.fxml"));
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        resetLabel();
        if(checkIfDouble())
        {
            if(checkIfRecipientExist())
            {
                if(checkIfSufficientFunds())
                {
                    sendCryptocurrency();
                    showInfo("Sent successfully");
                }
            }
        }
    }

    private void sendCryptocurrency()
    {
        RequestToSendCryptocurrency.getInstance().sendCryptocurrency(selectedCryptocurrency,Double.parseDouble(tfCryptocurrencyAmount.getText()),sendValidation.getLoadUserData().loadUser(tfRecipientNickname.getText()));
    }

    private boolean checkIfDouble()
    {
        if(!sendValidation.checkIfDouble(tfCryptocurrencyAmount.getText()).equals( Valid.VALID))
        {
            showInfo(sendValidation.checkIfDouble(tfCryptocurrencyAmount.getText()));
            return false;
        }
        return true;
    }

    private boolean checkIfSufficientFunds()
    {
        if(!sendValidation.checkIfSufficientFundsToSend(selectedCryptocurrency, tfCryptocurrencyAmount.getText()).equals(Valid.VALID))
        {
            showInfo(sendValidation.checkIfSufficientFundsToSend(selectedCryptocurrency,tfCryptocurrencyAmount.getText()));
            return false;
        }
        return true;
    }

    private boolean checkIfRecipientExist()
    {
        if(!sendValidation.checkIfRecipientExist(tfRecipientNickname.getText()).equals(Valid.VALID))
        {
            showInfo(sendValidation.checkIfRecipientExist(tfRecipientNickname.getText()));
            return false;
        }
        return true;
    }



    @FXML
    void cbxCryptocurrencyOnAction(ActionEvent event) {
        selectedCryptocurrency = cbxCryptocurrency.getSelectionModel().getSelectedItem();
    }

    private void setComboBoxItems() {
        ObservableList<String> cryptocurrency = FXCollections.observableArrayList("BTC", "DOGE", "ETH");
        cbxCryptocurrency.setItems(cryptocurrency);
        cbxCryptocurrency.getSelectionModel().selectFirst();
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
