package controller.account;

import controller.View;
import model.database.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.session.LoggedUser;


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

    private User user;
    
    @FXML
    private void initialize() {
        user = LoggedUser.getInstance().getLoggedUser();
        setUserData();
        setObtainedCurrency();
        setObtainedCryptocurrency();
    }

    @FXML
    void btnChangePasswordOnAction(ActionEvent event) {
        View.getInstance().setView(View.getView("ChangePasswordView.fxml"));
    }

    @FXML
    void btnChangePersonalDataOnAction(ActionEvent event) {
        View.getInstance().setView(View.getView("ChangePersonalDataView.fxml"));
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
        View.getInstance().setView(View.getView("MainMenuView.fxml"));
    }

    private void setUserData()
    {
        lbUsername.setText(user.getUsername());
        lbFirstName.setText(user.getFirstName());
        lbLastName.setText(user.getLastName());
        lbCountry.setText(user.getUserContact().getCountry());
        lbEmail.setText(user.getUserContact().getEmail());
        lbNumber.setText(user.getUserContact().getPhoneNumber());
    }

    private void setObtainedCryptocurrency()
    {
        lbBtcAmount.setText(String.valueOf(user.getUserWallet().getBtc()));
        lbEthAmount.setText(String.valueOf(user.getUserWallet().getEth()));
        lbDogeAmount.setText(String.valueOf(user.getUserWallet().getDoge()));
    }

    private void setObtainedCurrency()
    {
        lbEurAmount.setText(String.valueOf(user.getUserWallet().getEur()));
        lbPlnAmount.setText(String.valueOf(user.getUserWallet().getPln()));
        lbUsdAmount.setText(String.valueOf(user.getUserWallet().getUsd()));
    }
}
