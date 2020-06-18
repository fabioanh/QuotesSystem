package com.traderepublic.quotessystem.clients.messages;

import java.math.BigDecimal;
import java.util.Objects;

public class Quote implements Data{
    private final BigDecimal price;
    private final String isin;

    public Quote(BigDecimal price, String isin) {
        this.price = price;
        this.isin = isin;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getIsin() {
        return isin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Objects.equals(price, quote.price) &&
                Objects.equals(isin, quote.isin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, isin);
    }
}
