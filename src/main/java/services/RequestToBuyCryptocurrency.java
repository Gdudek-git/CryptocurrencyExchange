package services;

import database.entity.UserWallet;
import session.ChangeUserData;
import session.LoggedUser;

public final class RequestToBuyCryptocurrency {
    private static RequestToBuyCryptocurrency buyCryptocurrency = new RequestToBuyCryptocurrency();
    ChangeUserData changeUserData = new ChangeUserData();

    public static RequestToBuyCryptocurrency getInstance()
    {
        if(buyCryptocurrency==null)
        {
            buyCryptocurrency=new RequestToBuyCryptocurrency();
        }
        return buyCryptocurrency;
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


    public void buyCryptocurrency(String currencyToPayWith, String cryptocurrencyToBuy,double currencyAmount,double cryptocurrencyAmount)
    {
        UserWallet userWallet = LoggedUser.getInstance().getLoggedUser().getUserWallet();
        subtractCurrency(currencyToPayWith,currencyAmount,userWallet);
        addCryptocurrency(cryptocurrencyToBuy,cryptocurrencyAmount,userWallet);
        changeUserData.changeUserData();
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
