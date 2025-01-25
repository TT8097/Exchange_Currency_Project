package stock_aplication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import stock_aplication.Przetwarzanie_XML.ComputeXML;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

class ComputeXMLTest {

    HttpResponse<String> httpresponse =  Mockito.mock(HttpResponse.class);
    @Test
    void Normal_Pattern_Empty_String(){
        Mockito.when(httpresponse.body()).thenReturn("");
        Assertions.assertEquals(ComputeXML.ComputeXMLtoEchangeList(httpresponse).size(),0);
    }
    @Test
    void Normal_Pattern_Random_String(){
        Mockito.when(httpresponse.body()).thenReturn("SDfsdfasdfasdfasdf");
        Assertions.assertEquals(ComputeXML.ComputeXMLtoEchangeList(httpresponse).size(),0);
    }
    @Test
    void Normal_Pattern_Happy_Path(){
        String body = "<ArrayOfExchangeRatesTable xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "<ExchangeRatesTable>" +
                "<Table>A</Table>" +
                "<No>252/A/NBP/2024</No>" +
                "<EffectiveDate>2024-12-31</EffectiveDate>" +
                "<Rates>" +
                "<Rate>" +
                "<Currency>bat (Tajlandia)</Currency>" +
                "<Code>THB</Code>" +
                "<Mid>0.1199</Mid>" +
                "</Rate>"+
                "</Rates>" +
                "</ExchangeRatesTable>" +
                "</ArrayOfExchangeRatesTable>";
        Mockito.when(httpresponse.body()).thenReturn(body);
        Assertions.assertEquals(ComputeXML.ComputeXMLtoEchangeList(httpresponse).get(0).getKursSredni(),Float.parseFloat("0.1199"));
        Assertions.assertEquals(ComputeXML.ComputeXMLtoEchangeList(httpresponse).get(0).getWaluta(),"bat (Tajlandia)");
        Assertions.assertEquals(ComputeXML.ComputeXMLtoEchangeList(httpresponse).get(0).getKod(),"THB");
    }
    @Test
    void Exchange_Pattern_Empty_String(){
        Mockito.when(httpresponse.body()).thenReturn("");
        Assertions.assertEquals(ComputeXML.ComputeXMLtoDateList(httpresponse).size(),0);
    }
    @Test
    void Exchange_Pattern_Random_String(){
        Mockito.when(httpresponse.body()).thenReturn("SDfsdfasdfasdfasdf");
        Assertions.assertEquals(ComputeXML.ComputeXMLtoDateList(httpresponse).size(),0);
    }
    @Test
    void Exchange_Pattern_Happy_Path(){
        String body = "<ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "<Table>C</Table>" +
                "<Currency>dolar amerykański</Currency>" +
                "<Code>USD</Code>" +
                "<Rates>" +
                "<Rate>" +
                "<No>064/A/NBP/2016</No>" +
                "<EffectiveDate>2016-04-04</EffectiveDate>" +
                "<Mid>3.7254</Mid>" +
                "</Rate>" +
                "</Rates>" +
                "</ExchangeRatesSeries>";
        Mockito.when(httpresponse.body()).thenReturn(body);
        Assertions.assertEquals(ComputeXML.ComputeXMLtoDateList(httpresponse).get(0).getMidRate(),Float.parseFloat("3.7254"));
        Assertions.assertEquals(ComputeXML.ComputeXMLtoDateList(httpresponse).get(0).getLocalDate(), LocalDate.parse("2016-04-04", DateTimeFormatter.ISO_LOCAL_DATE));

    }
    @Test
    void Exchange_Pattern_Bad_Rate(){
        String body = "<ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "<Table>C</Table>" +
                "<Currency>dolar amerykański</Currency>" +
                "<Code>USD</Code>" +
                "<Rates>" +
                "<Rate>" +
                "<No>064/A/NBP/2016</No>" +
                "<EffectiveDate>2016-04-04</EffectiveDate>" +
                "<Mid>3.1q223</Mid>" +
                "</Rate>" +
                "</Rates>" +
                "</ExchangeRatesSeries>";
        Mockito.when(httpresponse.body()).thenReturn(body);
        Assertions.assertEquals(ComputeXML.ComputeXMLtoDateList(httpresponse).size(),0);
    }
}
