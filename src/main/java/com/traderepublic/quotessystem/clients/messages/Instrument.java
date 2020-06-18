package com.traderepublic.quotessystem.clients.messages;

import java.util.Objects;

public class Instrument implements Data {
    private final String description;
    private final String isin;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(isin, that.isin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, isin);
    }
}
