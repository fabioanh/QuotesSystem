package com.traderepublic.quotessystem.data;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Objects.equals(isin, quote.isin) &&
                Objects.equals(price, quote.price) &&
                Objects.equals(timestamp, quote.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isin, price, timestamp);
    }
}
