package stock_aplication.Szablony;

public class Kurs_Sredni_walut_Object {
    private String waluta;
    private String kod;
    private Float kursSredni;
    private int index;

    public Kurs_Sredni_walut_Object(String waluta, String kod, String kursSredni, int index) {
        this.waluta = waluta;
        this.kod = kod;
        this.kursSredni = setFloat(kursSredni);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public Float getKursSredni() {
        return kursSredni;
    }

    public void setKursSredni(Float kursSredni) {
        this.kursSredni = kursSredni;
    }

    private Float setFloat(String numberF){
        Float number=null;
        try{
            number=Float.parseFloat(numberF);
        }
        catch (NumberFormatException x ){
            return 0.0F;
        }
        return number;
    }
}
