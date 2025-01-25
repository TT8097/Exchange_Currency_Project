package stock_aplication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stock_aplication.Szablony.Kurs_Sredni_walut_Object;
import stock_aplication.Window_Main_Parts.Calculator;

public class CalculatorTest {
    Calculator testCalculator = new Calculator();
    Kurs_Sredni_walut_Object test = new Kurs_Sredni_walut_Object("test","test","1",0);
    @Test
    void Calculator_Happy_Path(){
        Assertions.assertEquals(testCalculator.calculate(test,"100"),"100.0");
    }
    @Test
    void Calculator_Many_Dots(){
        Assertions.assertEquals(testCalculator.calculate(test,"100..100"),"podales bledne dane");
    }
    @Test
    void Calculator_Letters_In_Number(){
        Assertions.assertEquals(testCalculator.calculate(test,"100.1223123n12312E"),"podales bledne dane");
    }
    @Test
    void Calculator_Empty_Ammount(){
        Assertions.assertEquals(testCalculator.calculate(test,""),"podales bledne dane");
    }
    @Test
    void Calculator_With_Coma(){
        Assertions.assertEquals(testCalculator.calculate(test,"123,123"),"podales bledne dane");
    }
    @Test
    void Calculator_Weird_Ammount(){
        Assertions.assertEquals(testCalculator.calculate(test,"000000123123"),"podales bledne dane");
    }
    @Test
    void Calculator_To_Big_Ammount(){
        Assertions.assertEquals(testCalculator.calculate(test,"435634563456345634563456435634563454356"),"podales bledne dane");
    }
    @Test
    void Calculator_With_Space_Ammount(){
        Assertions.assertEquals(testCalculator.calculate(test,"123. 123"),"123.1230");
    }
}
