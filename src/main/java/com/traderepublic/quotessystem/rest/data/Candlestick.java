package com.traderepublic.quotessystem.rest.data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Candlestick {
    private final Instant openTimestamp;
    private final BigDecimal openPrice;
    private final BigDecimal highPrice;
    private final BigDecimal lowPrice;
    private final BigDecimal closePrice;
    private final Instant closeTimestamp;

    public Candlestick(Instant openTimestamp, BigDecimal openPrice, BigDecimal highPrice, BigDecimal lowPrice, BigDecimal closePrice, Instant closeTimestamp) {
        this.openTimestamp = openTimestamp;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.closeTimestamp = closeTimestamp;
    }

    public Instant getOpenTimestamp() {
        return openTimestamp;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public Instant getCloseTimestamp() {
        return closeTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candlestick that = (Candlestick) o;
        return Objects.equals(openTimestamp, that.openTimestamp) &&
                Objects.equals(openPrice, that.openPrice) &&
                Objects.equals(highPrice, that.highPrice) &&
                Objects.equals(lowPrice, that.lowPrice) &&
                Objects.equals(closePrice, that.closePrice) &&
                Objects.equals(closeTimestamp, that.closeTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openTimestamp, openPrice, highPrice, lowPrice, closePrice, closeTimestamp);
    }
}
