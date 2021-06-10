package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import static ui.controllers.MainStage.getScene;

public class UserViewController {
    //region javafx controls
    @FXML
    private Label lbProfit_Loss;

    @FXML
    private Label lbBalance;

    @FXML
    private Label lbDeposit;

    @FXML
    private Label lbLoggedUser;

    @FXML
    private MenuItem miPLN;

    @FXML
    private MenuItem miUSD;

    @FXML
    private MenuItem miEUR;

    @FXML
    private MenuItem mi10s;

    @FXML
    private MenuItem mi1Min;

    @FXML
    private MenuItem mi10Min;

    @FXML
    private RadioButton rbHourly;

    @FXML
    private ToggleGroup chartUpdateGroup;

    @FXML
    private RadioButton rbDaily;

    @FXML
    private RadioButton rbWeekly;

    @FXML
    private TextField tfCurrencyType;

    @FXML
    private Label lbCurrentPrice;

    @FXML
    private Label lbAveragePrice;


    //endregion

    @FXML
    void currencyMenuOnAction(ActionEvent event) {

    }

    @FXML
    void updateMenuOnAction(ActionEvent event) {

    }


    @FXML
    void btnBuyOnAction(ActionEvent event) {

        getMainStage().setScene(getScene("BuyView.fxml"));
    }

    @FXML
    void btnExchangeOnAction(ActionEvent event) {
        getMainStage().setScene(getScene("ExchangeView.fxml"));
    }

    @FXML
    void btnSellOnAction(ActionEvent event) {
        getMainStage().setScene(getScene("SellView.fxml"));
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        getMainStage().setScene(getScene("SendView.fxml"));
    }

    @FXML
    void btnAccountOnAction(ActionEvent event) {
        getMainStage().setScene(getScene("AccountView.fxml"));
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        getMainStage().setScene(getScene("LoginView.fxml"));
    }

    private MainStage getMainStage() {
        return MainStage.getInstance();
    }

}