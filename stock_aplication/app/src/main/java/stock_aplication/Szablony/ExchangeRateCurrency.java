package stock_aplication.Szablony;

import java.time.LocalDate;

public class ExchangeRateCurrency {
    private LocalDate localDate;
    private Float midRate;


    public ExchangeRateCurrency(LocalDate localDate, Float midRate) {
        this.localDate = localDate;
        this.midRate = midRate;
    }

    public ExchangeRateCurrency() {

    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Float getMidRate() {
        return midRate;
    }

    public void setMidRate(Float midRate) {
        this.midRate = midRate;
    }
}
