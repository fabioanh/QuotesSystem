package com.traderepublic.quotessystem.clients;

import com.google.gson.Gson;
import com.traderepublic.quotessystem.clients.messages.QuoteMessage;
import com.traderepublic.quotessystem.data.MongodbClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuoteMessageHandler implements ProvidersMessageHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(QuoteMessageHandler.class);

    @Autowired
    private MongodbClient mongodbClient;

    public void handleMessage(QuoteMessage message) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Handling of message started for quote: {}",
                    new Gson().toJson(message));
        }
        mongodbClient.insertQuote(message);
    }

    public QuoteMessage parseMessage(String message) {
        return new Gson().fromJson(message, QuoteMessage.class);
    }
}
