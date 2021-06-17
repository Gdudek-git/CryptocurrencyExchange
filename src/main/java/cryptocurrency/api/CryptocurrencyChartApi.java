package cryptocurrency.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public abstract class CryptocurrencyChartApi {

    protected static ArrayList<Long> timestamps = new ArrayList<>();
    protected static ArrayList<Double> rates = new ArrayList<>();


    private void clearArrays()
    {
        timestamps.clear();
        rates.clear();
    }

    protected void getData(String selectedCryptocurrency, String selectedCurrency,String chartType)
    {
        clearArrays();
        String uriForRequest = getURI(selectedCryptocurrency,selectedCurrency,chartType);
        HttpClient client =HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uriForRequest)).build();
        sendRequest(client,request);

    }

    private void sendRequest(HttpClient client,HttpRequest request)
    {
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(CryptocurrencyChartApi::parseChartData)
                .join();
    }

    private static void parseChartData(String JSON_format)
    {
        JSONObject data = new JSONObject(JSON_format);
        JSONArray array = data.getJSONObject("Data").getJSONArray("Data");
        setChartData(array);
    }


    private static void setChartData( JSONArray array)
    {
        JSONObject tmp;
        for(int i=2;i<array.length();i++)
        {
            tmp=array.getJSONObject(i);
            rates.add(tmp.getDouble("open"));
            timestamps.add(tmp.getLong("time"));
        }

    }

    private String getURI(String selectedCryptocurrency, String selectedCurrency,String chartType)
    {
        if(chartType.equals("hours"))
        {
            return "https://min-api.cryptocompare.com/data/v2/histohour?fsym="+selectedCryptocurrency+"&tsym="+ selectedCurrency+"&limit=10";
        }
        else if(chartType.equals("days")) {
            return "https://min-api.cryptocompare.com/data/v2/histoday?fsym=" + selectedCryptocurrency + "&tsym=" + selectedCurrency + "&limit=10";
        }
        return "https://min-api.cryptocompare.com/data/v2/histominute?fsym=" + selectedCryptocurrency + "&tsym=" + selectedCurrency + "&limit=10";
    }
}
