package stock_aplication.Window_History_Parts;

import javafx.beans.property.FloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import stock_aplication.Szablony.ExchangeRateCurrency;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class HistoryTools {
    public static String URLSetter(Kurs_Sredni_walut_Object pickedCurrency,LocalDate localDate){
        StringBuilder builder = new StringBuilder();
        builder.append("https://api.nbp.pl/api/exchangerates/rates/a/");
        builder.append(pickedCurrency.getKod());
        builder.append("/");
        builder.append(localDate.minusYears(1L).format(DateTimeFormatter.ISO_LOCAL_DATE));
        builder.append("/");
        builder.append(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        builder.append("/");
        return builder.toString();
    }
    public static List<XYChart.Series<String,Number>> ConvertListToSeries(List<ExchangeRateCurrency> exchangeRateCurrencyList, List<LocalDate> dateperiods, FloatProperty maxChart,FloatProperty minChart){
        //XYChart.Series<LocalDate,Number> serie = new XYChart.Series<>();
       List<ObservableList<XYChart.Data<String,Number>>> datesToObservableListObjects = new ArrayList<>();
        //serie.setData(serieList);
        int index = 0;
         Float max=0F;
        Float min=Float.MAX_VALUE;
        dateperiods.forEach(x->datesToObservableListObjects.add(FXCollections.observableArrayList()));
        for (ExchangeRateCurrency exchangeRateCurrency: exchangeRateCurrencyList){
            XYChart.Data<String,Number> data = new XYChart.Data<String,Number>(exchangeRateCurrency.getLocalDate().toString(),exchangeRateCurrency.getMidRate());

            if(exchangeRateCurrency.getLocalDate().isAfter(dateperiods.get(index))){
                datesToObservableListObjects.get(index).add(data);
                if(exchangeRateCurrency.getMidRate()>max){max=exchangeRateCurrency.getMidRate();}
                else if(exchangeRateCurrency.getMidRate()<min){min=exchangeRateCurrency.getMidRate();}
            }
            else {

                index++;
                System.out.println(exchangeRateCurrency.getLocalDate());
                datesToObservableListObjects.get(index).add(duplicateData(datesToObservableListObjects.get(index-1).getLast()));
                datesToObservableListObjects.get(index).add(data);
                if(exchangeRateCurrency.getMidRate()>max){max=exchangeRateCurrency.getMidRate();}
                else if(exchangeRateCurrency.getMidRate()<min){min=exchangeRateCurrency.getMidRate();}
                }
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                SetToolTip(data);
            });
        }
        maxChart.set(max);
        minChart.set(min);
        return datesToObservableListObjects.stream().map(XYChart.Series::new).toList();
    }
    public static List<LocalDate> DatePeriodsGenerator(LocalDate localDate){
        List<Long> timeperiods= new ArrayList<>(Arrays.asList(5L,30L,90L,180L,367L));
        List<LocalDate> dataPeriodList = new ArrayList<>();
        for(Long x : timeperiods){

            dataPeriodList.add(localDate.minusDays(x));

        }
        return dataPeriodList;
    }
    public static void MenageAddSeries(int index, ObservableList<XYChart.Series<String,Number>> chartSeries,ObservableList<XYChart.Series<String,Number>> series){
        for (int i = chartSeries.size();i<=index;i++){
            chartSeries.add(series.get(i));

        }
    }
    public static void MenageDelSeries(int index, ObservableList<XYChart.Series<String,Number>> chartSeries){
        chartSeries.subList(index+1,chartSeries.size()).clear();
    }
    private static void SetToolTip(XYChart.Data<String,Number> data){
        Tooltip tooltip = new Tooltip("Data: "+data.getXValue()+" Kurs: "+data.getYValue().toString());
        tooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(data.getNode(),tooltip);
    }
    private static XYChart.Data<String,Number> duplicateData(XYChart.Data<String,Number> data)
    {
        XYChart.Data<String,Number> data_duplicate= new XYChart.Data<String,Number>(data.getXValue(), data.getYValue());
        data_duplicate.nodeProperty().addListener((observable, oldValue, newValue) -> {
            SetToolTip(data_duplicate);
        });

        return data_duplicate;
    }
}
