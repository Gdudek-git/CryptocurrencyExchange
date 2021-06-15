package cryptocurrency.api;

import cryptocurrency.SetCryptocurrencyExchangeRates;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public abstract class CryptocurrencyApi {

    private static SetCryptocurrencyExchangeRates setCryptocurrencyExchangeRates = new SetCryptocurrencyExchangeRates();
    private static String currentCurrency;
    private static String currentCryptocurrency;
    private static boolean isAverageRate;
    private static ArrayList<Double> cryptocurrencyRates = new ArrayList<>();




    public void getData(String cryptocurrency,boolean isAverageRate)
    {
        this.isAverageRate = isAverageRate;
        cryptocurrencyRates.clear();
        currentCryptocurrency=cryptocurrency;
        HttpClient client = HttpClient.newHttpClient();
        getPlnRate(client);
        getEurRate(client);
        getUsdRate(client);
        setRates();

    }

    private void getPlnRate(HttpClient client)
    {
        currentCurrency="PLN";
        sendRequest(client);
    }

    private void getEurRate(HttpClient client)
    {
        currentCurrency="EUR";
        sendRequest(client);
    }

    private void getUsdRate(HttpClient client)
    {
        currentCurrency="USD";
        sendRequest(client);
    }

    private void sendRequest(HttpClient client)
    {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://min-api.cryptocompare.com/data/pricemultifull?fsyms=" + currentCryptocurrency + "&tsyms="+currentCurrency )).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(CryptocurrencyApi::parseData)
                .join();
    }

    private static void parseData(String JSON_format)
    {
        JSONObject jsonObject = new JSONObject(JSON_format);
        jsonObject =jsonObject.getJSONObject("RAW").getJSONObject(currentCryptocurrency).getJSONObject(currentCurrency);
        getRates(jsonObject);
    }

    private static void getRates(JSONObject jsonObject)
    {
        if(isAverageRate)
        {
            cryptocurrencyRates.add((jsonObject.getDouble("HIGHDAY")+jsonObject.getDouble("LOWDAY"))/2);
        }
        else {
            cryptocurrencyRates.add(jsonObject.getDouble("PRICE"));
        }

    }

    private void setRates()
    {
        if(isAverageRate)
        {
            currentCryptocurrency="AVERAGE";
        }
        setCryptocurrencyExchangeRates.roundAndSetExchangeRates(currentCryptocurrency,cryptocurrencyRates);
    }



}
