package validation;

import com.mysql.cj.log.Log;
import database.entity.UserWallet;
import session.LoggedUser;

public final class Buy_Send_Sell_ExchangeViewsValidation {

    private static Buy_Send_Sell_ExchangeViewsValidation buySendSellExchangeViewsValidation;

    public static Buy_Send_Sell_ExchangeViewsValidation getInstance()
    {
        if(buySendSellExchangeViewsValidation ==null)
        {
            buySendSellExchangeViewsValidation =new Buy_Send_Sell_ExchangeViewsValidation();
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
