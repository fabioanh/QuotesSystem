package com.traderepublic.quotessystem.clients;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.traderepublic.quotessystem.clients.messages.Message;
import com.traderepublic.quotessystem.clients.messages.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class QuoteMessageHandler implements ProvidersMessageHandler{
    private final static Logger LOGGER = LoggerFactory.getLogger(QuoteMessageHandler.class);

    @Override
    public void handleMessage(String message) {
        Type collectionType = new TypeToken<Message<Quote>>(){}.getType();
        Message<Quote> myJson = new Gson().fromJson(message, collectionType);
        LOGGER.info("Data successfully parsed {}", myJson.getData());
    }
}
