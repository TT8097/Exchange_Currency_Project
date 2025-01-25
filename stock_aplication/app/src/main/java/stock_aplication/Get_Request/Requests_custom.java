package stock_aplication.Get_Request;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import stock_aplication.Przetwarzanie_XML.ComputeXML;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Requests_custom {
    public static HttpResponse<String> connectToURL(String urlStr){
        //List<Kurs_Sredni_walut_Object> listKursow= new ArrayList<>();
        HttpResponse<String> response= null;
        try { HttpClient client = HttpClient.newHttpClient();
            HttpRequest request= HttpRequest.newBuilder().uri(new URI(urlStr)).GET().header("Accept","application/xml").build();
            response = client.send(request,HttpResponse.BodyHandlers.ofString());

           // System.out.println( test_doc.getElementsByTagName("Rate").item(2).getChildNodes().item(2).getTextContent());
           // listKursow= ComputeXML.ComputeXMLtoEchangeList(response);
        } catch (URISyntaxException | IOException | InterruptedException  e) {
            return response;
        }
        return response;
    }
}
