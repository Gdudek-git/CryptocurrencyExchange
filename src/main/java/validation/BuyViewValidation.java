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
        return Valid.VALID;
    }

    public String checkIfSufficientFundsToBuy(String usedCurrency,String cryptocurrencyAmountToBuy)
    {
            double userFunds = getUserFunds(usedCurrency);
            if(userFunds<Double.parseDouble(cryptocurrencyAmountToBuy))
            {
                return "You don't have enough funds";
            }
        return Valid.VALID;

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
