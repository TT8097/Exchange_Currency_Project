package stock_aplication.Thread_Tasks;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import stock_aplication.Get_Request.Requests_custom;
import stock_aplication.Przetwarzanie_XML.ComputeXML;
import stock_aplication.Szablony.ExchangeRateCurrency;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;
import stock_aplication.Window_History_Parts.HistoryTools;

import java.time.LocalDate;
import java.util.List;

public class Tasks {
    public static Task<Void> SetObservableListWithAPIInfo(ObservableList<Kurs_Sredni_walut_Object> listKursow){

        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                listKursow.addAll(ComputeXML.ComputeXMLtoEchangeList(Requests_custom.connectToURL("https://api.nbp.pl/api/exchangerates/tables/A/last")));
                return null;
            }
        };
    }
    public static Task<Void> SetObservableSerieWithAPIInfo(ObservableList<Kurs_Sredni_walut_Object> listKursow){

        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                listKursow.addAll(ComputeXML.ComputeXMLtoEchangeList(Requests_custom.connectToURL("https://api.nbp.pl/api/exchangerates/tables/A/")));
                return null;
            }
        };
    }
    public static Task<Void> SetSeriesList(List<ExchangeRateCurrency> list,Kurs_Sredni_walut_Object pickedCurrency){
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                list.addAll(ComputeXML.ComputeXMLtoDateList(Requests_custom.connectToURL(HistoryTools.URLSetter(pickedCurrency,LocalDate.now()))));
                return null;
            }
        };
    }
}
