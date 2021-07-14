package model.operations;

import model.database.entity.UserWallet;
import model.session.LoggedUser;

public class SellCryptocurrencyModel {

    public void sellCryptocurrency(String cryptocurrencyToSell, String currencyToObtain,String cryptocurrencyAmount,String currencyAmount)
    {
        UserWallet userWallet = LoggedUser.getInstance().getLoggedUser().getUserWallet();
        subtractCryptocurrency(cryptocurrencyToSell,Double.parseDouble(cryptocurrencyAmount), userWallet);
        addCurrency(currencyToObtain,Double.parseDouble(currencyAmount), userWallet);
    }

    public void subtractCryptocurrency(String cryptocurrencyToSell, double cryptocurrencyAmount, UserWallet userWallet)
    {
        switch(cryptocurrencyToSell)
        {
            case"BTC": userWallet.setBtc(userWallet.getBtc()-cryptocurrencyAmount);break;
            case"ETH": userWallet.setEth(userWallet.getEth()-cryptocurrencyAmount);break;
            case"DOGE": userWallet.setDoge(userWallet.getDoge()-cryptocurrencyAmount);break;
        }
    }

    public void addCurrency(String currencyToObtain, double currencyAmount, UserWallet userWallet)
    {
        switch(currencyToObtain)
        {
            case"PLN": userWallet.setPln(userWallet.getPln()+currencyAmount);break;
            case"EUR": userWallet.setEur(userWallet.getEur()+currencyAmount);break;
            case"USD": userWallet.setUsd(userWallet.getUsd()+currencyAmount);break;
        }
    }
}
