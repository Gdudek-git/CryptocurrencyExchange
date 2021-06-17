package services;

import cryptocurrency.CryptocurrencyExchangeRates;
import javafx.application.Platform;
import ui.controllers.UserViewController;

import javax.swing.*;



public final class CryptocurrencyRatesUpdateTimer {
    private static CryptocurrencyRatesUpdateTimer cryptocurrencyRatesUpdateTimer;
    private Timer timer;
    private UserViewController userViewController;

    public void setUserViewController(UserViewController userViewController)
    {
        this.userViewController = userViewController;
    }
    public static CryptocurrencyRatesUpdateTimer getInstance()
    {
        if(cryptocurrencyRatesUpdateTimer ==null)
        {
            cryptocurrencyRatesUpdateTimer = new CryptocurrencyRatesUpdateTimer();
        }
        return cryptocurrencyRatesUpdateTimer;
    }

    public void startUpdating(int updateTime,String selectedCryptocurrency)
    {

         timer = new Timer(updateTime, arg0 -> {
            updateCryptocurrencyRates(selectedCryptocurrency);
            updateCryptocurrencyAverageRate(selectedCryptocurrency);
           Platform.runLater(()->{
                   userViewController.updateCryptocurrencyRatesUI();
                   userViewController.updateChart();
           });
        });
        timer.setRepeats(true);
        timer.start();


    }


    public void stopTimer()
    {
        if(timer!=null) {
          timer.stop();
        }
    }

    private void updateCryptocurrencyRates(String selectedCryptocurrency)
    {
        CryptocurrencyExchangeRates.getInstance().updateRates(selectedCryptocurrency,false);
    }

    private void updateCryptocurrencyAverageRate(String selectedCryptocurrency)
    {
        CryptocurrencyExchangeRates.getInstance().updateRates(selectedCryptocurrency,true);
    }
}
