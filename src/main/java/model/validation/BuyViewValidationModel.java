package model.validation;

import model.database.entity.UserWallet;
import model.session.LoggedUser;

public class BuyViewValidationModel {

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

    public String checkIfSufficientFundsToBuy(String usedCurrency,String currencyAmountToPay)
    {
            double userFunds = getUserFunds(usedCurrency);
            if(userFunds<Double.parseDouble(currencyAmountToPay))
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
