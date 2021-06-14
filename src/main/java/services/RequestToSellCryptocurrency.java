package services;

import database.entity.UserWallet;
import session.ChangeUserData;
import session.LoggedUser;

public final class RequestToSellCryptocurrency {

    private static RequestToSellCryptocurrency sellCryptocurrency;
    ChangeUserData changeUserData = new ChangeUserData();

    public static RequestToSellCryptocurrency getInstance()
    {
        if(sellCryptocurrency==null)
        {
            sellCryptocurrency=new RequestToSellCryptocurrency();
        }
        return sellCryptocurrency;
    }

    public void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> changeUserData.establishConnection()
        );
        thread.start();
    }

    public void closeConnectionWithDatabase()
    {
        changeUserData.closeConnection();
    }

    public void sellCryptocurrency(String cryptocurrencyToSell, String currencyToObtain,double cryptocurrencyAmount,double currencyAmount)
    {
        UserWallet userWallet = LoggedUser.getInstance().getLoggedUser().getUserWallet();
        substractCryptocurrency(cryptocurrencyToSell,cryptocurrencyAmount,userWallet);
        addCurrency(currencyToObtain,currencyAmount,userWallet);
        changeUserData.changeUserData();

    }

    public void substractCryptocurrency(String cryptocurrencyToSell, double cryptocurrencyAmount, UserWallet userWallet)
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
