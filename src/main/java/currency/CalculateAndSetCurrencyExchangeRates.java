package currency;

public class CalculateAndSetCurrencyExchangeRates {

    CurrencyExchangeRates currencyExchangeRates =  CurrencyExchangeRates.getInstance();

    public void setKnownRates(double usdToPlnBid,double usdToPlnAsk,double eurToPlnBid, double eurToPlnAsk)
    {
        currencyExchangeRates.setBid_Ask_Values(usdToPlnBid,usdToPlnAsk);
        currencyExchangeRates.setCurrencyExchangeRates("usdToPln");

        currencyExchangeRates.setBid_Ask_Values(eurToPlnBid,eurToPlnAsk);
        currencyExchangeRates.setCurrencyExchangeRates("eurToPln");

        calculateAndSetPlnToUsdBid_Ask(usdToPlnBid);
        calculateAndSetPlnToEurBid_Ask(eurToPlnBid);

    }


    private void calculateAndSetPlnToUsdBid_Ask(double usdToPlnBid)
    {
        double plnToUsdBid = 1/usdToPlnBid;
        double plnToUsdAsk = plnToUsdBid+((plnToUsdBid*1.98) /100);
        currencyExchangeRates.setBid_Ask_Values(plnToUsdBid,plnToUsdAsk);
        currencyExchangeRates.setCurrencyExchangeRates("plnToUsd");
    }


    private void calculateAndSetPlnToEurBid_Ask(double eurToPlnBid)
    {
        double plnToEurBid = 1/eurToPlnBid;
        double plnToEurAsk = plnToEurBid+((plnToEurBid*1.98)/100);
        currencyExchangeRates.setBid_Ask_Values(plnToEurBid,plnToEurAsk);
        currencyExchangeRates.setCurrencyExchangeRates("plnToEur");
    }


}
