package model.validation;

import model.database.entity.UserWallet;
import model.session.LoggedUser;

import java.math.BigDecimal;
import java.math.RoundingMode;

public  class SellViewValidationModel {

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


    public double getRoundedCurrencyExchangeRate(String currencyAmount,double currencyRate)
    {
        return round(String.valueOf(Double.parseDouble(currencyAmount)*currencyRate));
    }

    private double round(String currencyAmount) {
        BigDecimal bd = new BigDecimal(currencyAmount);
        bd = bd.setScale(2, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }

}
