package com.traderepublic.quotessystem.clients.messages;

public class Message<T extends Data> {
    private T data;
    private String type;

    public Message(T data, String type) {
        this.data = data;
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public String getType() {
        return type;
    }
}
