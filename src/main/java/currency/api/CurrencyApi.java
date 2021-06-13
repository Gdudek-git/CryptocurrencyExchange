package currency.api;


import currency.CalculateAndSetCurrencyExchangeRates;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class CurrencyApi {

    private static CalculateAndSetCurrencyExchangeRates calculateAndSetCurrencyExchangeRates = new CalculateAndSetCurrencyExchangeRates();

    public void getData()
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://api.nbp.pl/api/exchangerates/tables/C/?format=JSON" )).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(CurrencyApi::parseData)
                .join();
    }

    private static void parseData(String JSON_format)
    {
        JSONArray jsonArray = new JSONArray(JSON_format);
        jsonArray = jsonArray.getJSONObject(0).getJSONArray("rates");
        getRates(jsonArray);
    }

    private static void getRates(JSONArray jsonArray)
    {
        double usdToPlnBid = getUsdToPlnBid(jsonArray);
        double usdToPlnAsk = getUsdToPlnAsk(jsonArray);
        double eurToPlnBid = getEurToPlnBid(jsonArray);
        double eurToPlnAsk = getEurToPlnAsk(jsonArray);

        calculateAndSetCurrencyExchangeRates.setKnownRates(usdToPlnBid,usdToPlnAsk,eurToPlnBid,eurToPlnAsk);
    }


    private static double getUsdToPlnBid(JSONArray jsonArray) {
        JSONObject usdToPlnObject = jsonArray.getJSONObject(0);
        return usdToPlnObject.getDouble("bid");
    }

    private static double getUsdToPlnAsk(JSONArray jsonArray)
    {
        JSONObject usdToPlnObject = jsonArray.getJSONObject(0);
        return usdToPlnObject.getDouble("ask");
    }

    private static double getEurToPlnBid(JSONArray jsonArray)
    {
        JSONObject eurToPlnObject = jsonArray.getJSONObject(3);
        return eurToPlnObject.getDouble("bid");
    }

    private static double getEurToPlnAsk(JSONArray jsonArray)
    {
        JSONObject eurToPlnObject = jsonArray.getJSONObject(3);
        return eurToPlnObject.getDouble("ask");
    }





}
