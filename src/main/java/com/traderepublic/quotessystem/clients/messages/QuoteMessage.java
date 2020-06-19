package com.traderepublic.quotessystem.clients.messages;

import java.time.Instant;


public class QuoteMessage {
    private final Quote data;
    private final String type;
    private final Instant timestamp;

    public QuoteMessage() {
        this.data = null;
        this.type = null;
        this.timestamp = Instant.now();
    }

    public QuoteMessage(Quote data, String type, Instant timestamp) {
        this.data = data;
        this.type = type;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
    }

    public Quote getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

}
