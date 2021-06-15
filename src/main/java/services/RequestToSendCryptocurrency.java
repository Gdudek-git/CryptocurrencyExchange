package services;

import database.entity.User;
import database.entity.UserWallet;
import session.ChangeUserData;
import session.LoggedUser;

public final class RequestToSendCryptocurrency {
    private static RequestToSendCryptocurrency requestToSendCryptocurrency;
    ChangeUserData changeUserData = new ChangeUserData();
    public static RequestToSendCryptocurrency getInstance()
    {
        if(requestToSendCryptocurrency==null)
        {
            requestToSendCryptocurrency = new RequestToSendCryptocurrency();
        }
        return requestToSendCryptocurrency;
    }

    public void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> changeUserData.establishConnection()
        );
        thread.start();
    }

    public void closeConnectionWithDatabase()
    {
        changeUserData.closeConnection();
    }


    public void sendCryptocurrency(String cryptocurrencyToSend, double cryptocurrencyAmount, User recipient)
    {
        UserWallet userWallet = LoggedUser.getInstance().getLoggedUser().getUserWallet();
        subtractCryptocurrency(cryptocurrencyToSend,cryptocurrencyAmount,userWallet);
        changeUserData.changeUserData();
        userWallet = recipient.getUserWallet();
        addCurrency(cryptocurrencyToSend,cryptocurrencyAmount,userWallet);
        changeUserData.changeSelectedUserData(recipient);
    }

    private void subtractCryptocurrency(String cryptocurrencyToSend, double cryptocurrencyAmount, UserWallet userWallet)
    {
        switch(cryptocurrencyToSend)
        {
            case"BTC": userWallet.setBtc(userWallet.getBtc()-cryptocurrencyAmount);break;
            case"ETH": userWallet.setEth(userWallet.getEth()-cryptocurrencyAmount);break;
            case"DOGE": userWallet.setDoge(userWallet.getDoge()-cryptocurrencyAmount);break;
        }
    }

    private void addCurrency(String cryptocurrencyToSend, double cryptocurrencyAmount, UserWallet userWallet)
    {

        switch(cryptocurrencyToSend)
        {
            case"BTC": userWallet.setBtc(userWallet.getBtc()+cryptocurrencyAmount);break;
            case"DOGE": userWallet.setDoge(userWallet.getDoge()+cryptocurrencyAmount);break;
            case"ETH": userWallet.setEth(userWallet.getEth()+cryptocurrencyAmount);break;
        }
    }

}
