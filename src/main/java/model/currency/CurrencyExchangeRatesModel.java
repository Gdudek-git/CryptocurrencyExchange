package model.currency;

import model.currency.api.CurrencyApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyExchangeRatesModel extends CurrencyApi {

        private Map<String, List<Double>> exchangeRatesMap;

        public CurrencyExchangeRatesModel()
        {
            exchangeRatesMap = new HashMap<>();
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
            exchangeRatesMap.put("EURToPLN", currencyExchangeBid_Ask_ValuesArrayList);
            exchangeRatesMap.put("USDToPLN", currencyExchangeBid_Ask_ValuesArrayList);
            exchangeRatesMap.put("PLNToUSD", currencyExchangeBid_Ask_ValuesArrayList);
            exchangeRatesMap.put("PLNToEUR", currencyExchangeBid_Ask_ValuesArrayList);
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
