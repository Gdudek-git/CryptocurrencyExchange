package currency;

public class CalculateAndSetCurrencyExchangeRates {

    CurrencyExchangeRates currencyExchangeRates =  CurrencyExchangeRates.getInstance();

    public void setKnownRates(double usdToPlnBid,double usdToPlnAsk,double eurToPlnBid, double eurToPlnAsk)
    {
        currencyExchangeRates.setCurrencyExchangeRates("USDToPLN",usdToPlnBid,usdToPlnAsk);

        currencyExchangeRates.setCurrencyExchangeRates("EURToPLN",eurToPlnBid,eurToPlnAsk);

        calculateAndSetPlnToUsdBid_Ask(usdToPlnBid);
        calculateAndSetPlnToEurBid_Ask(eurToPlnBid);

    }


    private void calculateAndSetPlnToUsdBid_Ask(double usdToPlnBid)
    {
        double plnToUsdBid = 1/usdToPlnBid;
        double plnToUsdAsk = plnToUsdBid+((plnToUsdBid*3) /100);
        currencyExchangeRates.setCurrencyExchangeRates("PLNToUSD",plnToUsdBid,plnToUsdAsk);
    }


    private void calculateAndSetPlnToEurBid_Ask(double eurToPlnBid)
    {
        double plnToEurBid = 1/eurToPlnBid;
        double plnToEurAsk = plnToEurBid+((plnToEurBid*3)/100);
        currencyExchangeRates.setCurrencyExchangeRates("PLNToEUR",plnToEurBid,plnToEurAsk);
    }


}
