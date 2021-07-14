package model.operations;

import model.database.entity.User;
import model.database.entity.UserWallet;
import model.session.LoggedUser;

public class SendCryptocurrencyModel {



    public void sendCryptocurrency(String cryptocurrencyToSend, String cryptocurrencyAmount, User recipient)
    {
        UserWallet userWallet = LoggedUser.getInstance().getLoggedUser().getUserWallet();
        subtractCryptocurrencyFromSender(cryptocurrencyToSend,Double.parseDouble(cryptocurrencyAmount), userWallet);

        userWallet = recipient.getUserWallet();
        addCryptocurrencyToRecipient(cryptocurrencyToSend,Double.parseDouble(cryptocurrencyAmount), userWallet);
    }

    private void subtractCryptocurrencyFromSender(String cryptocurrencyToSend, double cryptocurrencyAmount, UserWallet userWallet)
    {
        switch(cryptocurrencyToSend)
        {
            case"BTC": userWallet.setBtc(userWallet.getBtc()-cryptocurrencyAmount);break;
            case"ETH": userWallet.setEth(userWallet.getEth()-cryptocurrencyAmount);break;
            case"DOGE": userWallet.setDoge(userWallet.getDoge()-cryptocurrencyAmount);break;
        }
    }

    private void addCryptocurrencyToRecipient(String cryptocurrencyToSend, double cryptocurrencyAmount, UserWallet userWallet)
    {

        switch(cryptocurrencyToSend)
        {
            case"BTC": userWallet.setBtc(userWallet.getBtc()+cryptocurrencyAmount);break;
            case"DOGE": userWallet.setDoge(userWallet.getDoge()+cryptocurrencyAmount);break;
            case"ETH": userWallet.setEth(userWallet.getEth()+cryptocurrencyAmount);break;
        }
    }

}
