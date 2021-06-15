package services;

import database.entity.UserWallet;
import session.ChangeUserData;
import session.LoggedUser;

public final class RequestToExchangeCurrency {
    private static RequestToExchangeCurrency exchangeCurrency;
    ChangeUserData changeUserData = new ChangeUserData();

    public static RequestToExchangeCurrency getInstance()
    {
        if(exchangeCurrency==null)
        {
            exchangeCurrency = new RequestToExchangeCurrency();
        }
        return exchangeCurrency;
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

    public void exchangeCurrency(String currencyToExchange, String currencyToObtain,double currencyToExchangeWithAmount,double currencyToObtainAmount)
    {
        UserWallet userWallet = LoggedUser.getInstance().getLoggedUser().getUserWallet();
        subtractCurrency(currencyToExchange,currencyToExchangeWithAmount,userWallet);
        addCurrency(currencyToObtain,currencyToObtainAmount,userWallet);
        changeUserData.changeUserData();

    }

    public void subtractCurrency(String currencyToExchangeWithAmount, double currencyAmount, UserWallet userWallet)
    {
        switch(currencyToExchangeWithAmount)
        {
            case"PLN": userWallet.setPln(userWallet.getPln()-currencyAmount);break;
            case"EUR": userWallet.setEur(userWallet.getEur()-currencyAmount);break;
            case"USD": userWallet.setUsd(userWallet.getUsd()-currencyAmount);break;
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
