package com.traderepublic.quotessystem.data;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.Instant;

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
}
