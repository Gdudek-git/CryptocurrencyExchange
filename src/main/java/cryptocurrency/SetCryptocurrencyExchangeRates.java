package cryptocurrency;

import java.util.ArrayList;

public class SetCryptocurrencyExchangeRates {


    public void setExchangeRates(String cryptocurrency, ArrayList rates)
    {
        CryptocurrencyExchangeRates.getInstance().setCryptocurrencyExchangeRates(cryptocurrency,(double)rates.get(0),(double)rates.get(1),(double)rates.get(2));
    }
}
