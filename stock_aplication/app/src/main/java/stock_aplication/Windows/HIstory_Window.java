package stock_aplication.Windows;


import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stock_aplication.Szablony.ExchangeRateCurrency;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;
import stock_aplication.Thread_Tasks.Tasks;
import stock_aplication.Window_History_Parts.HistoryTools;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HIstory_Window  {
    ObservableList<XYChart.Series<String,Number>> series = FXCollections.observableArrayList();
    LineChart<String,Number> test;
    Kurs_Sredni_walut_Object pickedCurrency;
    FloatProperty minval= new SimpleFloatProperty(0F);
    FloatProperty maxval= new SimpleFloatProperty(0F);


    public HIstory_Window(Kurs_Sredni_walut_Object pickedCurrency) {
        this.pickedCurrency = pickedCurrency;
    }

    public void HistoryStage(Stage primaryStage) {
        HBox root = new HBox();
        HBox.setHgrow(root, Priority.ALWAYS);
        VBox.setVgrow(root,Priority.ALWAYS);
        test();
       // new Thread(Tasks.SetObservableListWithAPIInfo(listaKursow)).start();
        root.getChildren().add(StockChartPart());//jakims cudem to dziala ze sie samo dzieli

        Scene test_scene = new Scene(root);
        //Task<Void> task1= Tasks.SetObservableListWithAPIInfo();
        primaryStage.setScene(test_scene);
        primaryStage.show();
    }
    public VBox StockChartPart(){
        VBox rootStock= new VBox();
        VBox.setVgrow(rootStock,Priority.ALWAYS);
        HBox.setHgrow(rootStock,Priority.ALWAYS);
        VBox boxStockChart= new VBox();
        VBox boxStockTimePick= new VBox();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        yAxis.lowerBoundProperty().bind(minval);
        yAxis.upperBoundProperty().bind(maxval);
        yAxis.setTickUnit(0.05);
        //test.getData().addAll(series);
        test=new LineChart<String,Number>(xAxis,yAxis);
        test.prefWidthProperty().bind(rootStock.widthProperty());
        test.prefHeightProperty().bind(rootStock.heightProperty().multiply(0.90));// to jest juz gotowy schemat glowny rozmieszczenia
        boxStockChart.getChildren().add(test);

        boxStockTimePick.prefWidthProperty().bind(rootStock.widthProperty());
        boxStockTimePick.prefHeightProperty().bind(rootStock.heightProperty().multiply(0.10));

        //boxStockTimePick.getChildren().add(test1);
        rootStock.getChildren().addAll(boxStockChart,boxStockTimePick);
        TimeButtons(new ArrayList<>(Arrays.asList("5d","1m","3m","6m","1r")),boxStockTimePick);

        return rootStock;
    }
    public void TimeButtons(List<String> timeIntervals, VBox root){
        List<Button> buttonList =timeIntervals.stream().map(x->{
            int index = timeIntervals.indexOf(x);
            Button button = new Button(x);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(test.getData().contains(series.get(index))){
                        HistoryTools.MenageDelSeries(index,test.getData());
                    }else HistoryTools.MenageAddSeries(index,test.getData(),series);
                    System.out.println(index);
                }
            });
        return button;}
        ).toList();
        HBox buttomContainer = new HBox();
        buttomContainer.getChildren().addAll(buttonList);
        HBox.setHgrow(buttomContainer,Priority.ALWAYS);
        VBox.setVgrow(buttomContainer,Priority.ALWAYS);
        buttomContainer.setAlignment(Pos.CENTER);
        root.getChildren().add(buttomContainer);
    }
    public void test (){
        List<ExchangeRateCurrency> list = new ArrayList<>();
        Thread proces = new Thread(Tasks.SetSeriesList(list,pickedCurrency));
        try {
            proces.start();
            proces.join();
            series.addAll(HistoryTools.ConvertListToSeries(list, HistoryTools.DatePeriodsGenerator(LocalDate.now()),maxval,minval));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
