package model;

import javafx.application.Platform;
import controller.UserViewController;
import javax.swing.*;

public  class TimerModel {

    private Timer timer;
    private UserViewController userViewController;

    public void setUserViewController(UserViewController userViewController)
    {
        this.userViewController = userViewController;
    }

    public void startUpdating(int updateTime)
    {

         timer = new Timer(updateTime, arg0 -> {
            userViewController.updateRates();
            Platform.runLater(()->{
                   userViewController.setCryptocurrencyRatesUI();
                   userViewController.setChart();
           });
        });
        timer.setRepeats(true);
        timer.start();
    }

    public int changeUpdateRate(String selectedUpdateRate)
    {
        if(selectedUpdateRate.equals("10s"))
        {
            return 10000;
        }
        else if(selectedUpdateRate.equals("1min"))
        {
            return 60000;
        }
        else
        {
            return 600000;
        }
    }



    public void stopTimer()
    {
        if(timer!=null) {
          timer.stop();
        }
    }


}
