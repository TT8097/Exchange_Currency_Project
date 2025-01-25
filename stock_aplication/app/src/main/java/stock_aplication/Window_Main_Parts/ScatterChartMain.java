package stock_aplication.Window_Main_Parts;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;

public class ScatterChartMain {
    public ScatterChart<String,Number> test(ObservableList<Kurs_Sredni_walut_Object> listaKursow, IntegerProperty selectItem){

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final ScatterChart<String,Number> scatterChart = new ScatterChart<>(xAxis,yAxis);
        ObservableList<XYChart.Data<String, Number>> chartData = FXCollections.observableArrayList();
        XYChart.Series<String,Number> serie = new XYChart.Series<>();
        serie.setData(chartData);

        listaKursow.addListener(new ListChangeListener<Kurs_Sredni_walut_Object>() {
            @Override
            public void onChanged(Change<? extends Kurs_Sredni_walut_Object> c) {
                Platform.runLater(()->{
                    for (Kurs_Sredni_walut_Object x : c.getList()){
                        XYChart.Data<String,Number> data = new XYChart.Data<String,Number>(x.getKod(),x.getKursSredni());
                        data.nodeProperty().addListener(new ChangeListener<Node>() {
                            @Override
                            public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                                newValue.setOnMouseClicked(event->{selectItem.set(x.getIndex());});
                                setToolTip(data);
                            }
                        });
                        chartData.add(data);
                    }

                });
            }
        });
        scatterChart.getData().add(serie);
        return scatterChart;
    }
    private void setToolTip(XYChart.Data<String,Number> data){
        Tooltip tooltip= new Tooltip("Waluta: "+data.getXValue()+" Kurs: "+data.getYValue());
        tooltip.setShowDelay(Duration.ONE);
        Tooltip.install(data.getNode(),tooltip);
    }
}
