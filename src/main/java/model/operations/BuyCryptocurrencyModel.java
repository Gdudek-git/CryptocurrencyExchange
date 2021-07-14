package model.operations;

import model.database.entity.UserWallet;
import model.session.LoggedUser;

public class BuyCryptocurrencyModel {

    public void buyCryptocurrency(String currencyToPayWith, String cryptocurrencyToBuy,String currencyAmount,String cryptocurrencyAmount)
    {
        UserWallet userWallet = LoggedUser.getInstance().getLoggedUser().getUserWallet();
        subtractCurrency(currencyToPayWith,Double.parseDouble(currencyAmount), userWallet);
        addCryptocurrency(cryptocurrencyToBuy,Double.parseDouble(cryptocurrencyAmount), userWallet);
    }


    public void addCryptocurrency(String cryptocurrencyToBuy, double cryptocurrencyAmount, UserWallet userWallet)
    {
        switch(cryptocurrencyToBuy)
        {
            case"BTC": userWallet.setBtc(userWallet.getBtc()+cryptocurrencyAmount);break;
            case"ETH": userWallet.setEth(userWallet.getEth()+cryptocurrencyAmount);break;
            case"DOGE": userWallet.setDoge(userWallet.getDoge()+cryptocurrencyAmount);break;
        }
    }

    public void subtractCurrency(String currencyToPayWith, double currencyAmount, UserWallet userWallet)
    {

        switch(currencyToPayWith)
        {
            case"PLN": userWallet.setPln(userWallet.getPln()-currencyAmount);break;
            case"EUR": userWallet.setEur(userWallet.getEur()-currencyAmount);break;
            case"USD": userWallet.setUsd(userWallet.getUsd()-currencyAmount);break;
        }
    }

}
