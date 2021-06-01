package main.java.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserViewController
{
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

    }

    @FXML
    void btnExchangeOnAction(ActionEvent event) {

    }

    @FXML
    void btnSellOnAction(ActionEvent event) {

    }

    @FXML
    void btnSendOnAction(ActionEvent event) {

    }

    @FXML
    void btnAccountOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

}