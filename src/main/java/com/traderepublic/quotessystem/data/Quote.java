package com.traderepublic.quotessystem.data;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

public class Quote {
    private final String isin;
    private final BigDecimal price;
    private final Instant timestamp;

    @BsonCreator
    public Quote(@BsonProperty("isin") String isin, @BsonProperty("price") BigDecimal price, @BsonProperty("timestamp") Instant timestamp) {
        this.isin = isin;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getIsin() {
        return isin;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
