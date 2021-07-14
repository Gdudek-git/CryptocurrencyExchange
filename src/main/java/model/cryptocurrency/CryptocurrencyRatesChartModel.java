package model.cryptocurrency;

import model.cryptocurrency.api.CryptocurrencyChartApi;

import javafx.scene.chart.LineChart;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class CryptocurrencyRatesChartModel extends CryptocurrencyChartApi {

    @Override
    protected void getData(String selectedCryptocurrency, String selectedCurrency, String chartType) {
        super.getData(selectedCryptocurrency, selectedCurrency, chartType);
    }


    public void createChart(LineChart<?,?> chart, String selectedCryptocurrency, String selectedCurrency, String chartType, NumberAxis y)
    {
        chart.getData().clear();
        y.setForceZeroInRange(false);
        getData(selectedCryptocurrency,selectedCurrency,chartType);
        XYChart.Series series = new XYChart.Series();
        setSeries(series,chartType);
        series.setName("Rates");
        chart.getData().add(series);
    }

    private void setSeries(XYChart.Series series,String chartType)
    {
        for(int i=0;i<rates.size();i++)
        {
            series.getData().add(new XYChart.Data<>(convertTimestamp(timestamps.get(i),chartType),rates.get(i)));
        }
    }



    private String convertTimestamp(long timestamp,String chartType)
    {
        Instant instant = Instant.ofEpochSecond(timestamp);
        Timestamp tmp = Timestamp.from(instant);
        Date date = new Date(tmp.getTime());
        SimpleDateFormat dateFormat;

        if(chartType.equals("days")) {
            dateFormat = new SimpleDateFormat("dd/MM");
        }
        else
        {
            dateFormat = new SimpleDateFormat("HH:mm");
        }

        return dateFormat.format(date);
    }


    public String changeChartType(String selectedChartType)
    {
        if(selectedChartType.equals("Hourly"))
        {
            return "hours";
        }
        else if(selectedChartType.equals("Daily")) {
            return "days";
        }
        else
        {
            return "minutes";
        }
    }
}
