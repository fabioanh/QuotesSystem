package com.traderepublic.quotessystem.clients.messages;

public class Instrument implements Data {
    private String description;
    private String isin;

    public Instrument(String description, String isin) {
        this.description = description;
        this.isin = isin;
    }

    public String getDescription() {
        return description;
    }

    public String getIsin() {
        return isin;
    }
}
