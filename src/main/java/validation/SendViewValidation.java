package validation;

import database.entity.UserWallet;
import session.LoadUserData;
import session.LoggedUser;

public final class SendViewValidation {
    private static SendViewValidation viewValidation;
    LoadUserData loadUserData = new LoadUserData();
    public static SendViewValidation getInstance()
    {
        if(viewValidation==null)
        {
            viewValidation = new SendViewValidation();
        }
        return viewValidation;
    }

    public LoadUserData getLoadUserData()
    {
        return loadUserData;
    }


    public String checkIfDouble(String enteredCryptocurrency)
    {
        try{
            Double.valueOf(enteredCryptocurrency);
        }
        catch (Exception ex){
            return ("Not a valid double value");
        }
        return "valid";
    }

    public String checkIfSufficientFundsToSend(String selectedCryptocurrency, String amountToSend)
    {
        if(getUserCryptocurrencyFunds(selectedCryptocurrency)<Double.parseDouble(amountToSend))
        {
            return "You don't have enough funds";
        }
        return "valid";
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

    public String checkIfRecipientExist(String recipientNickname)
    {
        if(loadUserData.loadUser(recipientNickname)==null)
        {
            return "Recipient don't exist";
        }
        return "valid";
    }

    public void establishConnectionWithDatabase()
    {
        loadUserData.establishConnection();
    }

    public void closeConnectionWithDatabase()
    {
        loadUserData.closeConnection();
    }


}
