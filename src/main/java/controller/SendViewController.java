package controller;


import model.database.DatabaseConnectionModel;
import model.database.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import model.operations.SendCryptocurrencyModel;
import model.session.ChangeUserDataModel;
import model.session.LoadUserModel;
import model.validation.SendViewValidationModel;
import model.validation.Valid;

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

    private  DatabaseConnectionModel databaseConnectionModel;
    private SendCryptocurrencyModel sendCryptocurrencyModel;
    private ChangeUserDataModel changeUserDataModel;
    private SendViewValidationModel sendViewValidationModel;
    private LoadUserModel loadUserModel;

    private Session session;

    private String selectedCryptocurrency;

    @FXML
    public void initialize() {
        databaseConnectionModel = new DatabaseConnectionModel();
        sendCryptocurrencyModel = new SendCryptocurrencyModel();
        changeUserDataModel = new ChangeUserDataModel();
        sendViewValidationModel = new SendViewValidationModel();
        loadUserModel = new LoadUserModel();

        selectedCryptocurrency = "BTC";

        establishConnectionWithDatabase();
        setComboBoxItems();
    }

    private void establishConnectionWithDatabase() {
        Thread thread = new Thread(() -> session =  databaseConnectionModel.getSessionObj());
        thread.start();
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

    private void resetLabel()
    {
        lbInfo.setText("");
    }

    private boolean checkIfDouble()
    {
        String isDouble = sendViewValidationModel.checkIfDouble(tfCryptocurrencyAmount.getText());
        if(!isDouble.equals( Valid.VALID))
        {
            showInfo(isDouble);
            return false;
        }
        return true;
    }

    private boolean checkIfRecipientExist()
    {
        String recipientExist = sendViewValidationModel.checkIfRecipientExist(tfRecipientNickname.getText(),session,loadUserModel);
        if(!recipientExist.equals(Valid.VALID))
        {
            showInfo(recipientExist);
            return false;
        }
        return true;
    }

    private boolean checkIfSufficientFunds()
    {
        String sufficientFunds = sendViewValidationModel.checkIfSufficientFundsToSend(selectedCryptocurrency,tfCryptocurrencyAmount.getText());
        if(!sufficientFunds.equals(Valid.VALID))
        {
            showInfo(sufficientFunds);
            return false;
        }
        return true;
    }

    private void sendCryptocurrency()
    {
        User recipient = loadUserModel.loadUser(tfRecipientNickname.getText(),session);
        sendCryptocurrencyModel.sendCryptocurrency(selectedCryptocurrency,tfCryptocurrencyAmount.getText(),recipient);
        changeUserDataModel.updateLoggedUserData(session);
        changeUserDataModel.updateSelectedUserData(recipient,session);
        showInfo("Send successfully");
    }

    private void showInfo(String info)
    {
        lbInfo.setText(info);
    }

    @FXML
    void btnReturnOnAction(ActionEvent event)
    {
        databaseConnectionModel.closeConnection(session);
        View.getInstance().setView(View.getView("UserView.fxml"));
    }
}
