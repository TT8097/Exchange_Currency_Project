package stock_aplication.Window_Main_Parts;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;

public class ListViewKurs {
    private final ListView<Kurs_Sredni_walut_Object> listView = new ListView<>();

    public ListView<Kurs_Sredni_walut_Object> getListView() {
        return listView;
    }

    public ListView<Kurs_Sredni_walut_Object> listViewExchange(ObservableList<Kurs_Sredni_walut_Object> listaKursow, IntegerProperty selectItem){

        listView.setItems(listaKursow);
        listView.setCellFactory(new Callback<ListView<Kurs_Sredni_walut_Object>, ListCell<Kurs_Sredni_walut_Object>>() {
            @Override
            public ListCell<Kurs_Sredni_walut_Object> call(ListView<Kurs_Sredni_walut_Object> param) {
                return new ListCell<Kurs_Sredni_walut_Object>(){
                    @Override
                    protected void updateItem(Kurs_Sredni_walut_Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            HBox rootCell= new HBox();
                            HBox.setHgrow(rootCell, Priority.ALWAYS);
                            VBox.setVgrow(rootCell,Priority.ALWAYS);
                            HBox currencie= new HBox(new Text(item.getWaluta()));
                            HBox code= new HBox(new Text(item.getKod()));
                            HBox exchangeRate= new HBox(new Text(String.valueOf(item.getKursSredni())));
                            HBox.setHgrow(currencie,Priority.ALWAYS);
                            HBox.setHgrow(code,Priority.ALWAYS);
                            HBox.setHgrow(exchangeRate,Priority.ALWAYS);
                            rootCell.getChildren().addAll(currencie,code,exchangeRate);
                            setGraphic(rootCell);}
                        else {setGraphic(null);}
                    }
                };
            }
        });
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectItem.set(listView.getSelectionModel().getSelectedItems().getFirst().getIndex());
            }
        });
        return listView;
    }
}
