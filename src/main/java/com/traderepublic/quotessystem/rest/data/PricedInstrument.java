package com.traderepublic.quotessystem.rest.data;

import java.math.BigDecimal;
import java.time.Instant;

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
}
