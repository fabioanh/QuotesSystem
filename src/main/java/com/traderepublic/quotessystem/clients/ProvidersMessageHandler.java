package com.traderepublic.quotessystem.clients;

import com.traderepublic.quotessystem.clients.messages.Message;

public interface ProvidersMessageHandler {
    void handleMessage(String message);

    Message parseMessage(String message);
}
