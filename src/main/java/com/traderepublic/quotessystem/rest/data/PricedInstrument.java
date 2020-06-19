package com.traderepublic.quotessystem.rest.data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class PricedInstrument {
    private final String isin;
    private final String description;
    private final BigDecimal price;

    public PricedInstrument(String isin, String description, BigDecimal price) {
        this.isin = isin;
        this.description = description;
        this.price = price;
    }

    public String getIsin() {
        return isin;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricedInstrument that = (PricedInstrument) o;
        return Objects.equals(isin, that.isin) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isin, description, price);
    }
}
