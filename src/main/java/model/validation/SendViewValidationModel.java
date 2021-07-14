package model.validation;

import model.database.entity.UserWallet;
import org.hibernate.Session;
import model.session.LoadUserModel;
import model.session.LoggedUser;

public  class SendViewValidationModel {

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

    public String checkIfSufficientFundsToSend(String selectedCryptocurrency, String amountToSend)
    {
        if(getUserCryptocurrencyFunds(selectedCryptocurrency)<Double.parseDouble(amountToSend))
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

    public String checkIfRecipientExist(String recipientNickname, Session session, LoadUserModel loadUserModel)
    {
        if(loadUserModel.loadUser(recipientNickname,session)==null)
        {
            return "Recipient don't exist";
        }
        return Valid.VALID;
    }




}
