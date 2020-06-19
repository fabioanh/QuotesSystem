package com.traderepublic.quotessystem.data;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.Instant;
import java.util.Objects;

public class Instrument {
    private final String isin;
    private final String description;
    private final Instant timestamp;

    @BsonCreator
    public Instrument(@BsonProperty("isin") String isin, @BsonProperty("description") String description, @BsonProperty("timestamp") Instant timestamp) {
        this.isin = isin;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getIsin() {
        return isin;
    }

    public String getDescription() {
        return description;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return Objects.equals(isin, that.isin) &&
                Objects.equals(description, that.description) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isin, description, timestamp);
    }
}
