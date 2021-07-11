package controller;

import database.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import session.LoadUserData;
import session.LoggedUser;


public class AccountViewController {

    //region Controls
    @FXML
    private Label lbBtcAmount;

    @FXML
    private Label lbDogeAmount;

    @FXML
    private Label lbEurAmount;

    @FXML
    private Label lbEthAmount;

    @FXML
    private Label lbPlnAmount;

    @FXML
    private Label lbUsdAmount;

    @FXML
    private Label lbUsername;

    @FXML
    private Label lbFirstName;

    @FXML
    private Label lbLastName;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbNumber;

    @FXML
    private Label lbCountry;
    //endregion


    @FXML
    private void initialize() {
        updateUserInfo();
        setUserAccountInfoLabels();
    }

    @FXML
    void btnChangePasswordOnAction(ActionEvent event) {
        getMainStage().setScene(View.getScene("ChangePasswordView.fxml"));
    }

    @FXML
    void btnChangePersonalDataOnAction(ActionEvent event) {
        getMainStage().setScene(View.getScene("ChangePersonalDataView.fxml"));
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
        getMainStage().setScene(View.getScene("UserView.fxml"));
    }


    private void updateUserInfo()
    {
        LoadUserData.getInstance().establishConnection();
        LoggedUser.getInstance().setLoggedUser(LoadUserData.getInstance().loadUser(LoggedUser.getInstance().getLoggedUser().getUsername()));
        LoadUserData.getInstance().closeConnection();
    }

    private void setUserAccountInfoLabels()
    {
        User user = LoggedUser.getInstance().getLoggedUser();
        setObtainedCryptocurrency(user);
        setObtainedCurrency(user);
        setUserData(user);

    }

    private void setUserData(User user)
    {
        lbUsername.setText(user.getUsername());
        lbFirstName.setText(user.getFirstName());
        lbLastName.setText(user.getLastName());
        lbCountry.setText(user.getUserContact().getCountry());
        lbEmail.setText(user.getUserContact().getEmail());
        lbNumber.setText(user.getUserContact().getPhoneNumber());
    }

    private void setObtainedCryptocurrency(User user)
    {
        lbBtcAmount.setText(String.valueOf(user.getUserWallet().getBtc()));
        lbEthAmount.setText(String.valueOf(user.getUserWallet().getEth()));
        lbDogeAmount.setText(String.valueOf(user.getUserWallet().getDoge()));
    }

    private void setObtainedCurrency(User user)
    {
        lbEurAmount.setText(String.valueOf(user.getUserWallet().getEur()));
        lbPlnAmount.setText(String.valueOf(user.getUserWallet().getPln()));
        lbUsdAmount.setText(String.valueOf(user.getUserWallet().getUsd()));
    }

    private View getMainStage()
    {
        return View.getInstance();
    }
}
