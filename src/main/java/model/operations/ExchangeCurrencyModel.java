package model.operations;

import model.database.entity.UserWallet;
import model.session.LoggedUser;


public  class ExchangeCurrencyModel {

    public void exchangeCurrency(String currencyToExchange, String currencyToObtain,String currencyToExchangeWithAmount,String currencyToObtainAmount)
    {
        UserWallet userWallet = LoggedUser.getInstance().getLoggedUser().getUserWallet();
        subtractCurrency(currencyToExchange,Double.parseDouble(currencyToExchangeWithAmount), userWallet);
        addCurrency(currencyToObtain,Double.parseDouble(currencyToObtainAmount), userWallet);
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

    public double getHowMuchUserCanExchange(String selectedCurrency, String enteredAmount, double currencyToObtainRate)
    {
        if(selectedCurrency.equals("PLN"))
        {
            return Double.parseDouble(enteredAmount)/currencyToObtainRate;
        }
        else
        {
            return Double.parseDouble(enteredAmount)*currencyToObtainRate;
        }
    }

}
