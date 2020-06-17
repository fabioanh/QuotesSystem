package com.traderepublic.quotessystem.clients.messages;

import java.math.BigDecimal;

public class Quote implements Data{
    private BigDecimal price;
    private String isin;

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
}
