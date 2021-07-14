package model.currency;

public abstract class CalculateAndSetCurrencyExchangeRates {

    public void setKnownRates(double usdToPlnBid,double usdToPlnAsk,double eurToPlnBid, double eurToPlnAsk,CurrencyExchangeRatesModel currencyExchangeRatesModel)
    {
        currencyExchangeRatesModel.setCurrencyExchangeRates("USDToPLN",usdToPlnBid,usdToPlnAsk);

        currencyExchangeRatesModel.setCurrencyExchangeRates("EURToPLN",eurToPlnBid,eurToPlnAsk);

        calculateAndSetPlnToUsdBid_Ask(usdToPlnBid,currencyExchangeRatesModel);
        calculateAndSetPlnToEurBid_Ask(eurToPlnBid,currencyExchangeRatesModel);
    }

    private void calculateAndSetPlnToUsdBid_Ask(double usdToPlnBid,CurrencyExchangeRatesModel currencyExchangeRatesModel)
    {
        double plnToUsdBid = 1/usdToPlnBid;
        double plnToUsdAsk = plnToUsdBid+((plnToUsdBid*3) /100);
        currencyExchangeRatesModel.setCurrencyExchangeRates("PLNToUSD",plnToUsdBid,plnToUsdAsk);
    }


    private void calculateAndSetPlnToEurBid_Ask(double eurToPlnBid,CurrencyExchangeRatesModel currencyExchangeRatesModel)
    {
        double plnToEurBid = 1/eurToPlnBid;
        double plnToEurAsk = plnToEurBid+((plnToEurBid*3)/100);
        currencyExchangeRatesModel.setCurrencyExchangeRates("PLNToEUR",plnToEurBid,plnToEurAsk);
    }


}
