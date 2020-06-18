package com.traderepublic.quotessystem.clients.messages;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message<T extends Data> {
    private final T data;
    private final String type;
    private final LocalDateTime date;

    public Message(T data, String type) {
        this.data = data;
        this.type = type;
        this.date = LocalDateTime.now();
    }

    public T getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message<?> message = (Message<?>) o;
        return Objects.equals(data, message.data) &&
                Objects.equals(type, message.type) &&
                Objects.equals(date, message.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, type, date);
    }
}
