package validation;

import database.entity.UserWallet;
import session.LoggedUser;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class SellViewValidation {
    private static SellViewValidation sellValidation;

    public static SellViewValidation getInstance()
    {
        if(sellValidation==null)
        {
            sellValidation=new SellViewValidation();
        }
        return sellValidation;
    }


    public String checkIfDouble(String enteredCryptocurrency)
    {
        try{
            Double.valueOf(enteredCryptocurrency);
        }
        catch (Exception ex){
            return ("Not a valid double value");
        }
        return Valid.VALID;
    }


    public String checkIfSufficientFundsToSell(String selectedCryptocurrency, String amountToSell)
    {
        if(getUserCryptocurrencyFunds(selectedCryptocurrency)<Double.parseDouble(amountToSell))
        {
            return "You don't have enough funds";
        }
        return Valid.VALID;
    }


    private double getUserCryptocurrencyFunds(String selectedCryptocurrency)
    {
        UserWallet userWallet = LoggedUser.getInstance().getLoggedUser().getUserWallet();
        switch (selectedCryptocurrency)
        {
            case "BTC": return userWallet.getBtc();
            case "DOGE": return userWallet.getDoge();
            case "ETH": return userWallet.getEth();
        }
        return 0;
    }


    public double getRoundedCurrency(double currencyAmount)
    {
        return round(currencyAmount);
    }

    private double round(double currencyAmount) {
        BigDecimal bd = new BigDecimal(Double.toString(currencyAmount));
        bd = bd.setScale(2, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }

}
