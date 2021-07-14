package model.currency.api;


import model.currency.CalculateAndSetCurrencyExchangeRates;
import model.currency.CurrencyExchangeRatesModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class CurrencyApi extends CalculateAndSetCurrencyExchangeRates {


    private static double usdToPlnBid;
    private static double usdToPlnAsk;
    private static double eurToPlnBid;
    private static double eurToPlnAsk;

    public void getData()
    {
        HttpClient client = HttpClient.newHttpClient();
        sendRequest(client);
        setKnownRates(usdToPlnBid,usdToPlnAsk,eurToPlnBid,eurToPlnAsk,(CurrencyExchangeRatesModel)this);
    }

    private void sendRequest(HttpClient client)
    {
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
         usdToPlnBid = getUsdToPlnBid(jsonArray);
         usdToPlnAsk = getUsdToPlnAsk(jsonArray);
         eurToPlnBid = getEurToPlnBid(jsonArray);
         eurToPlnAsk = getEurToPlnAsk(jsonArray);
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
