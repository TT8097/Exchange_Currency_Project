package stock_aplication.Przetwarzanie_XML;


import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import stock_aplication.Szablony.ExchangeRateCurrency;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import java.io.StringReader;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public class ComputeXML {

    public static List<Kurs_Sredni_walut_Object> ComputeXMLtoEchangeList(HttpResponse<String> response){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<Kurs_Sredni_walut_Object> listaKursow = new ArrayList<>();
        factory.setNamespaceAware(true);
        Document xmlDocument;
        try {
            xmlDocument=factory.newDocumentBuilder().parse(new InputSource(new StringReader(response.body())));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            return listaKursow;
        }

        NodeList rates = xmlDocument.getElementsByTagName("Rate");
        for (int i = 0 ; i<rates.getLength();i++){
            NodeList rate = rates.item(i).getChildNodes();
            if(rate.getLength()<3||!rate.item(2).getTextContent().matches("^\\d+[.]?\\d+$")){continue;}
            listaKursow.add(new Kurs_Sredni_walut_Object(
                    rate.item(0).getTextContent(),
                    rate.item(1).getTextContent(),
                    rate.item(2).getTextContent(),i)

            );
        }
        return listaKursow;
    }
    public static List<ExchangeRateCurrency> ComputeXMLtoDateList(HttpResponse<String> response){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<ExchangeRateCurrency> exchangeRateCurrencyList = new ArrayList<>();
        factory.setNamespaceAware(true);
        Document xmlDocument;
        try {
            xmlDocument=factory.newDocumentBuilder().parse(new InputSource(new StringReader(response.body())));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            return exchangeRateCurrencyList;
        }

        NodeList rates = xmlDocument.getElementsByTagName("Rate");
        for(int i = rates.getLength()-1; i>=0;i--){
            NodeList rate = rates.item(i).getChildNodes();
            if(rate.getLength()<3||!rate.item(2).getTextContent().matches("^\\d+[.]?\\d+$")) continue;
            ExchangeRateCurrency temp = new ExchangeRateCurrency();
            temp.setLocalDate(LocalDate.parse(rate.item(1).getTextContent(),DateTimeFormatter.ISO_LOCAL_DATE));
            temp.setMidRate(Float.parseFloat(rate.item(2).getTextContent()));
            exchangeRateCurrencyList.add(temp);

        }
        return exchangeRateCurrencyList;
    }
}
