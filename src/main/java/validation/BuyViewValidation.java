package validation;

import database.entity.UserWallet;
import session.LoggedUser;

public final class BuyViewValidation {

    private static BuyViewValidation buySendSellExchangeViewsValidation;

    public static BuyViewValidation getInstance()
    {
        if(buySendSellExchangeViewsValidation ==null)
        {
            buySendSellExchangeViewsValidation =new BuyViewValidation();
        }
        return buySendSellExchangeViewsValidation;
    }

    public String checkIfDouble(String number)
    {
        try{
            Double.valueOf(number);
        }
        catch (Exception ex){
            return ("Not a valid double value");
        }
        return "valid";
    }

    public String checkIfSufficientFundsToBuy(String usedCurrency,double enteredCurrencyAmount,double enteredCryptocurrencyAmount,double currencyAmountForOneCryptocurrency)
    {
        double totalToPay = currencyAmountForOneCryptocurrency*enteredCryptocurrencyAmount;
        if(totalToPay>enteredCurrencyAmount)
        {
            return "Entered currency amount is insufficient";
        }
        else
        {
            double userFunds = getUserFunds(usedCurrency);
            if(userFunds<totalToPay)
            {
                return "You don't have enough funds";
            }
            return "valid";
        }
    }

    public double getUserFunds(String usedCurrency)
    {
        UserWallet userWallet = LoggedUser.getInstance().getLoggedUser().getUserWallet();
        switch (usedCurrency)
        {
            case "PLN": return userWallet.getPln();
            case "EUR": return userWallet.getEur();
            case "USD": return userWallet.getUsd();
        }
        return 0;
    }


}
