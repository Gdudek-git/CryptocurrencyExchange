package controller.operation_on_wallet;


import view.AvailableViews;
import view.View;
import model.database.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.operations.SendCryptocurrencyModel;
import model.session.LoadUserModel;
import model.validation.SendViewValidationModel;
import model.validation.Valid;

public class SendViewController extends AbstractOperationOnWalletViewController {

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

    private SendCryptocurrencyModel sendCryptocurrencyModel;
    private SendViewValidationModel sendViewValidationModel;
    private LoadUserModel loadUserModel;


    private String selectedCryptocurrency;

    @FXML
    public void initialize() {
        sendCryptocurrencyModel = new SendCryptocurrencyModel();
        sendViewValidationModel = new SendViewValidationModel();
        loadUserModel = new LoadUserModel();

        selectedCryptocurrency = "BTC";

        super.establishConnectionWithDatabase();
        setComboBoxItems();
    }

    private void setComboBoxItems() {
        ObservableList<String> cryptocurrency = FXCollections.observableArrayList("BTC", "DOGE", "ETH");
        cbxCryptocurrency.setItems(cryptocurrency);
        cbxCryptocurrency.getSelectionModel().selectFirst();
    }

    @FXML
    void cbxCryptocurrencyOnAction(ActionEvent event) {
        selectedCryptocurrency = cbxCryptocurrency.getSelectionModel().getSelectedItem();
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        super.resetLabel(lbInfo);
        if(checkIfDouble())
        {
            if(checkIfRecipientExist())
            {
                if(checkIfSufficientFunds())
                {
                    sendCryptocurrency();
                    super.showInfo(lbInfo,"Sent successfully");
                }
            }
        }
    }

    private boolean checkIfDouble()
    {
        String isDouble = sendViewValidationModel.checkIfDouble(tfCryptocurrencyAmount.getText());
        if(!isDouble.equals( Valid.VALID))
        {
            super.showInfo(lbInfo,isDouble);
            return false;
        }
        return true;
    }

    private boolean checkIfRecipientExist()
    {
        String recipientExist = sendViewValidationModel.checkIfRecipientExist(tfRecipientNickname.getText(),super.getSession(),loadUserModel);
        if(!recipientExist.equals(Valid.VALID))
        {
            super.showInfo(lbInfo,recipientExist);
            return false;
        }
        return true;
    }

    private boolean checkIfSufficientFunds()
    {
        String sufficientFunds = sendViewValidationModel.checkIfSufficientFundsToSend(selectedCryptocurrency,tfCryptocurrencyAmount.getText());
        if(!sufficientFunds.equals(Valid.VALID))
        {
            super.showInfo(lbInfo,sufficientFunds);
            return false;
        }
        return true;
    }

    private void sendCryptocurrency()
    {
        User recipient = loadUserModel.loadUser(tfRecipientNickname.getText(),super.getSession());
        sendCryptocurrencyModel.sendCryptocurrency(selectedCryptocurrency,tfCryptocurrencyAmount.getText(),recipient);
        super.updateLoggedUserData();
        super.getChangeUserDataModel().updateSelectedUserData(recipient,super.getSession());
        super.showInfo(lbInfo,"Send successfully");
    }


    @FXML
    void btnReturnOnAction(ActionEvent event)
    {
        super.closeConnection();
        View.getInstance().setView(View.getView(AvailableViews.MAIN_MENU_VIEW));
    }
}
