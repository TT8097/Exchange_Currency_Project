package stock_aplication;


import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import stock_aplication.Szablony.ExchangeRateCurrency;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;
import stock_aplication.Window_History_Parts.HistoryTools;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mockStatic;


public class HistoryToolsTest {
    LocalDate testDate = LocalDate.of(2025, 1, 1);
    @Test
    void URLSetter_Normal(){
        Kurs_Sredni_walut_Object testCurrency = Mockito.mock(Kurs_Sredni_walut_Object.class);
        Mockito.when(testCurrency.getKod()).thenReturn("usd");
        Assertions.assertEquals(HistoryTools.URLSetter(testCurrency,testDate),"https://api.nbp.pl/api/exchangerates/rates/a/usd/2024-01-01/2025-01-01/");
    }
    @Test
    void Date_Periods_Setter_Normal(){
        List<LocalDate> testListPeriods= HistoryTools.DatePeriodsGenerator(testDate);
        Assertions.assertEquals(testListPeriods.size(),5);
        Assertions.assertEquals(testListPeriods.get(0),LocalDate.of(2024,12,27));
        Assertions.assertEquals(testListPeriods.get(1),LocalDate.of(2024,12,2));
        Assertions.assertEquals(testListPeriods.get(2),LocalDate.of(2024,10,3));
        Assertions.assertEquals(testListPeriods.get(3),LocalDate.of(2024,7,5));
        Assertions.assertEquals(testListPeriods.get(4),LocalDate.of(2023,12,31));
    }
@Test
    void Menage_Add_Series_From_Zero(){
    ObservableList<XYChart.Series<String,Number>> chartSeries = FXCollections.observableArrayList();
    ObservableList<XYChart.Series<String,Number>> series = Mockito.mock(FXCollections.observableArrayList().getClass());
    Mockito.when(series.size()).thenReturn(5);
    HistoryTools.MenageAddSeries(4,chartSeries,series);
    Assertions.assertEquals(chartSeries.size(),5);
    }
    @Test
    void Menage_Add_Series_From_One_To_Three(){
        ObservableList<XYChart.Series<String,Number>> chartSeries = FXCollections.observableArrayList();
        chartSeries.add(new XYChart.Series<>());
        chartSeries.add(new XYChart.Series<>());
        ObservableList<XYChart.Series<String,Number>> series = Mockito.mock(FXCollections.observableArrayList().getClass());
        Mockito.when(series.size()).thenReturn(5);
        HistoryTools.MenageAddSeries(3,chartSeries,series);
        Assertions.assertEquals(chartSeries.size(),4);
    }
    @Test
    void Menage_Add_Series_From_One_To_One(){
        ObservableList<XYChart.Series<String,Number>> chartSeries = FXCollections.observableArrayList();
        chartSeries.add(new XYChart.Series<>());
        chartSeries.add(new XYChart.Series<>());
        ObservableList<XYChart.Series<String,Number>> series = Mockito.mock(FXCollections.observableArrayList().getClass());
        Mockito.when(series.size()).thenReturn(5);
        HistoryTools.MenageAddSeries(1,chartSeries,series);
        Assertions.assertEquals(chartSeries.size(),2);
    }
    @Test
    void Menage_Del_Series_From_Four_To_Zero(){
        ObservableList<XYChart.Series<String,Number>> chartSeries =FXCollections.observableArrayList();
        chartSeries.add(new XYChart.Series<>());
        chartSeries.add(new XYChart.Series<>());
        chartSeries.add(new XYChart.Series<>());
        chartSeries.add(new XYChart.Series<>());
        chartSeries.add(new XYChart.Series<>());

        HistoryTools.MenageDelSeries(0,chartSeries);
        Assertions.assertEquals(chartSeries.size(),1);
    }
    @Test
    void Menage_Del_Series_From_Three_To_One(){
        ObservableList<XYChart.Series<String,Number>> chartSeries = FXCollections.observableArrayList();
        chartSeries.add(new XYChart.Series<>());
        chartSeries.add(new XYChart.Series<>());
        chartSeries.add(new XYChart.Series<>());
        chartSeries.add(new XYChart.Series<>());


        HistoryTools.MenageDelSeries(1,chartSeries);
        Assertions.assertEquals(chartSeries.size(),2);
    }
    @Test
    void Menage_Del_Series_From_One_To_One(){
        ObservableList<XYChart.Series<String,Number>> chartSeries = FXCollections.observableArrayList();
        chartSeries.add(new XYChart.Series<>());
        chartSeries.add(new XYChart.Series<>());
        HistoryTools.MenageDelSeries(1,chartSeries);
        Assertions.assertEquals(chartSeries.size(),2);
    }
    @Test
    void Convert_List_To_Series_Size(){
        List<ExchangeRateCurrency> exchangeRateCurrencyList = new ArrayList<>();
        List<LocalDate> dateperiods = HistoryTools.DatePeriodsGenerator(testDate);
        FloatProperty maxChart = new SimpleFloatProperty(0F);
        FloatProperty minChart = new SimpleFloatProperty(0F);
        List<XYChart.Series<String,Number>> testConvert = HistoryTools.ConvertListToSeries(exchangeRateCurrencyList,dateperiods,maxChart,minChart);
        Assertions.assertEquals(testConvert.size(),5);
    }
    @Test
    void Convert_List_To_Series_Right_selections(){
        List<ExchangeRateCurrency> exchangeRateCurrencyList = new ArrayList<>();
        exchangeRateCurrencyList.add(new ExchangeRateCurrency(testDate.minusDays(2),0.0F));
        exchangeRateCurrencyList.add(new ExchangeRateCurrency(testDate.minusDays(20),0.0F));
        exchangeRateCurrencyList.add(new ExchangeRateCurrency(testDate.minusDays(40),0.0F));
        exchangeRateCurrencyList.add(new ExchangeRateCurrency(testDate.minusDays(60),0.0F));
        exchangeRateCurrencyList.add(new ExchangeRateCurrency(testDate.minusDays(180),0.0F));
        exchangeRateCurrencyList.add(new ExchangeRateCurrency(testDate.minusDays(300),0.0F));
        List<LocalDate> dateperiods = HistoryTools.DatePeriodsGenerator(testDate);
        FloatProperty maxChart = new SimpleFloatProperty(0F);
        FloatProperty minChart = new SimpleFloatProperty(0F);
        List<XYChart.Series<String,Number>> testConvert = HistoryTools.ConvertListToSeries(exchangeRateCurrencyList,dateperiods,maxChart,minChart);
        Assertions.assertEquals(testConvert.get(0).getData().size(),1);
        Assertions.assertEquals(testConvert.get(1).getData().size(),2);
        Assertions.assertEquals(testConvert.get(2).getData().size(),3);
        Assertions.assertEquals(testConvert.get(4).getData().size(),2);
        // w tescie jets o +1 dla kazdego odcinku czasu poniewaz dodaje do kazdego odcinku ostatni punkt z poprzedniego odcinka
        // zeby serie mialy ciag na wykresie
    }

}
