package com.traderepublic.quotessystem.clients.messages;

import java.time.Instant;


public class InstrumentMessage {
    private final Instrument data;
    private final String type;
    private final Instant timestamp;

    public InstrumentMessage() {
        this.data = null;
        this.type = null;
        this.timestamp = Instant.now();
    }

    public InstrumentMessage(Instrument data, String type, Instant timestamp) {
        this.data = data;
        this.type = type;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
    }

    public Instrument getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

}
