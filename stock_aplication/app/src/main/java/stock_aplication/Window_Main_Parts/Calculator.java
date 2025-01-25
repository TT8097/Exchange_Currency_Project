package stock_aplication.Window_Main_Parts;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Calculator {


    private boolean isNormal = true;
    private Text textFrom = new Text("---");
    private Text textTo = new Text("Polski zloty");
    public void setKursobject(Kurs_Sredni_walut_Object kursobject) {
        this.kursobject = kursobject;
    }

    private Kurs_Sredni_walut_Object kursobject;
    public void calculator(VBox root){


        Button switchButton = new Button("switch");
        switchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                isNormal = !isNormal;
                String memory = textFrom.getText();
                textFrom.setText(textTo.getText());
                textTo.setText(memory);

            }
        });
        TextField inputFrom = new TextField();
        TextField inputTo = new TextField();
        Button calculate = new Button("calculate");
        calculate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                inputTo.setText(calculate(kursobject,inputFrom.getText()));
            }
        });
        root.getChildren().addAll(textFrom,inputFrom,switchButton,textTo,inputTo,calculate);
    }
    public String calculate(Kurs_Sredni_walut_Object currencyObject, String amount){
        amount=amount.replaceAll("\\s+","");
        if(amount.matches("^\\d+[.]?\\d+$")&&currencyObject!=null&&amount.length()<10){
            BigDecimal result = new BigDecimal(amount);
            return isNormal ? result.multiply(BigDecimal.valueOf(currencyObject.getKursSredni()), MathContext.DECIMAL64).toString():result.divide(BigDecimal.valueOf(currencyObject.getKursSredni()), RoundingMode.CEILING).toString();}
        else {
            return "podales bledne dane";}
    }

    public void setTextFrom(String text) {
        (isNormal ? textFrom : textTo ).setText(text);
    }
}
