package ui.controllers;

import database.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import session.LoggedUser;

import static ui.controllers.MainStage.getScene;


public class AccountViewController {

    //region Controllers
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
        setUserAccountInfoLabels();
    }

    @FXML
    void btnChangePasswordOnAction(ActionEvent event) {
        getMainStage().setScene(getScene("ChangePasswordView.fxml"));
    }

    @FXML
    void btnChangePersonalDataOnAction(ActionEvent event) {
        getMainStage().setScene(getScene("ChangePersonalDataView.fxml"));
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
        getMainStage().setScene(getScene("UserView.fxml"));
    }

    private MainStage getMainStage()
    {
        return MainStage.getInstance();
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
}
