package currency;

import currency.api.CurrencyApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CurrencyExchangeRates extends CurrencyApi{

        private static CurrencyExchangeRates currencyExchangeRates;
        private Map<String, List<Double>> exchangeRatesMap = new HashMap<>();

       public static CurrencyExchangeRates getInstance()
    {
        if(currencyExchangeRates==null)
        {
            currencyExchangeRates = new CurrencyExchangeRates();
        }
        return currencyExchangeRates;
    }


        private CurrencyExchangeRates()
        {
            setMapKeys();
        }

        public Map<String, List<Double>> getExchangeRates()
        {
            return  exchangeRatesMap;
        }

        private void setMapKeys()
        {
            List<Double> currencyExchangeBid_Ask_ValuesArrayList = new ArrayList<>();
            currencyExchangeBid_Ask_ValuesArrayList.add(0.0);
            currencyExchangeBid_Ask_ValuesArrayList.add(0.0);
            exchangeRatesMap.put("eurToPln", currencyExchangeBid_Ask_ValuesArrayList);
            exchangeRatesMap.put("usdToPln", currencyExchangeBid_Ask_ValuesArrayList);
            exchangeRatesMap.put("plnToUsd", currencyExchangeBid_Ask_ValuesArrayList);
            exchangeRatesMap.put("plnToEur", currencyExchangeBid_Ask_ValuesArrayList);
        }


        public void setCurrencyExchangeRates(String key,double bid, double ask)
        {
            List<Double> currencyExchangeBid_Ask_ValuesArrayList = new ArrayList<>();
            currencyExchangeBid_Ask_ValuesArrayList.add(bid);
            currencyExchangeBid_Ask_ValuesArrayList.add(ask);
            exchangeRatesMap.replace(key, currencyExchangeBid_Ask_ValuesArrayList);
        }


        public void updateRates()
        {
            getData();
        }
}
