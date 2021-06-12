package currency;

import currency.api.CurrencyApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyExchangeRates {

        private static CurrencyExchangeRates currencyExchangeRates = new CurrencyExchangeRates();
        private Map<String, List<Double>> exchangeRatesMap = new HashMap<>();
        private CurrencyApi currencyApi;
        private List<Double> currencyExchangeBid_Ask_ValuesArrayList;

       public static CurrencyExchangeRates getInstance()
    {
        return currencyExchangeRates;
    }


        private CurrencyExchangeRates()
        {
            setMapKeys();
            currencyApi=new CurrencyApi();
            updateRates();
        }

        public Map<String, List<Double>> getExchangeRates()
        {
            return  exchangeRatesMap;
        }

        private void setMapKeys()
        {
            currencyExchangeBid_Ask_ValuesArrayList = new ArrayList<>();
            currencyExchangeBid_Ask_ValuesArrayList.add(0.0);
            currencyExchangeBid_Ask_ValuesArrayList.add(0.0);
            exchangeRatesMap.put("eurToPln", currencyExchangeBid_Ask_ValuesArrayList);
            exchangeRatesMap.put("usdToPln", currencyExchangeBid_Ask_ValuesArrayList);
            exchangeRatesMap.put("plnToUsd", currencyExchangeBid_Ask_ValuesArrayList);
            exchangeRatesMap.put("plnToEur", currencyExchangeBid_Ask_ValuesArrayList);
        }


        public void setBid_Ask_Values(double bid, double ask)
        {
            currencyExchangeBid_Ask_ValuesArrayList = new ArrayList<>();
            currencyExchangeBid_Ask_ValuesArrayList.add(bid);
            currencyExchangeBid_Ask_ValuesArrayList.add(ask);
        }

        public void addValues(String key)
        {
            exchangeRatesMap.replace(key, currencyExchangeBid_Ask_ValuesArrayList);
        }


        public void updateRates()
        {
            currencyApi.getData();
        }
}
