package stock_aplication.Windows;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;
import stock_aplication.Thread_Tasks.Tasks;
import stock_aplication.Window_History_Parts.HistoryTools;
import stock_aplication.Window_Main_Parts.Calculator;
import stock_aplication.Window_Main_Parts.ListViewKurs;
import stock_aplication.Window_Main_Parts.ScatterChartMain;

import javax.management.Notification;

public class Main_Window extends Application {
    // na changelistenery musisz uzyc property
    ObservableList<Kurs_Sredni_walut_Object> listaKursow= FXCollections.observableArrayList();
    IntegerProperty selectItem = new SimpleIntegerProperty(-1);
    Calculator calculator = new Calculator();
    ListViewKurs listViewKurs= new ListViewKurs();

    @Override
    public void start(Stage primaryStage) throws Exception {
        selectItem.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Kurs_Sredni_walut_Object memory = listaKursow.get(newValue.intValue());
                calculator.setTextFrom(memory.getWaluta());
                calculator.setKursobject(memory);
                listViewKurs.getListView().getSelectionModel().select(selectItem.get());
            }
        });
        HBox root = new HBox();
        HBox.setHgrow(root, Priority.ALWAYS);
        VBox.setVgrow(root,Priority.ALWAYS);
        new Thread(Tasks.SetObservableListWithAPIInfo(listaKursow)).start();
        root.getChildren().addAll(StockChartPart(),listAndXYZ());//jakims cudem to dziala ze sie samo dzieli
        Scene test_scene = new Scene(root);

        primaryStage.setScene(test_scene);
        primaryStage.show();
    }

    public VBox StockChartPart(){
        VBox rootStock= new VBox();
        VBox.setVgrow(rootStock,Priority.ALWAYS);
        HBox.setHgrow(rootStock,Priority.ALWAYS);
        VBox boxStockChart= new VBox();
        VBox boxStockTimePick= new VBox();
        ScatterChart<String,Number> test = new ScatterChartMain().test(listaKursow,selectItem);
        test.prefWidthProperty().bind(rootStock.widthProperty());
        test.prefHeightProperty().bind(rootStock.heightProperty().multiply(0.90));// to jest juz gotowy schemat glowny rozmieszczenia
        boxStockChart.getChildren().add(test);
        Button test1 = new Button("2");
        boxStockTimePick.prefWidthProperty().bind(rootStock.widthProperty());
        boxStockTimePick.prefHeightProperty().bind(rootStock.heightProperty().multiply(0.10));

        //boxStockTimePick.getChildren().add(test1);
        rootStock.getChildren().addAll(boxStockChart,boxStockTimePick);
        TimeButtons(boxStockTimePick);
        return rootStock;
    }
    public void TimeButtons(VBox root){
        Button timeChartButton = new Button("Time Perspective View");
        timeChartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectItem.get() != -1) {
                    new HIstory_Window(listaKursow.get(selectItem.get())).HistoryStage(new Stage());
                } else {
                    new Alert(Alert.AlertType.NONE, "nie wybrales waluty", ButtonType.OK).show();
                }
            }
        });
        HBox buttomContainer = new HBox();
        buttomContainer.getChildren().add(timeChartButton);
        HBox.setHgrow(buttomContainer,Priority.ALWAYS);
        VBox.setVgrow(buttomContainer,Priority.ALWAYS);
        buttomContainer.setAlignment(Pos.CENTER);
        root.getChildren().add(buttomContainer);
    }
    public VBox listAndXYZ(){
        VBox root = new VBox();
        VBox.setVgrow(root,Priority.ALWAYS);
        HBox.setHgrow(root,Priority.ALWAYS);

        VBox listviewbox = new VBox();
        VBox xyzbox = new VBox();

        listviewbox.prefWidthProperty().bind(root.widthProperty());
        xyzbox.prefWidthProperty().bind(root.widthProperty());
        VBox.setVgrow(listviewbox,Priority.ALWAYS);
        VBox.setVgrow(xyzbox,Priority.ALWAYS);;
        calculator.calculator(root);


        listviewbox.getChildren().add( listViewKurs.listViewExchange(listaKursow,selectItem));


        root.getChildren().addAll(listviewbox,xyzbox);
        return root;
    }


}
