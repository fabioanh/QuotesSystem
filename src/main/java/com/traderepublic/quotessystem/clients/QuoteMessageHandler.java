package com.traderepublic.quotessystem.clients;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.traderepublic.quotessystem.data.MongodbClient;
import com.traderepublic.quotessystem.data.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class QuoteMessageHandler{
    private final static Logger LOGGER = LoggerFactory.getLogger(QuoteMessageHandler.class);

    @Autowired
    private MongodbClient mongodbClient;

    public void handleMessage(JsonObject message) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Handling of message started for quote: {}",
                    new Gson().toJson(message));
        }
        Quote quote = new Quote(message.get("data").getAsJsonObject().get("isin").getAsString(),
                new BigDecimal(message.get("data").getAsJsonObject().get("price").getAsString()),
                Instant.now());
        mongodbClient.insertQuote(quote);
    }

    public JsonObject parseMessage(String message) {
        return new Gson().fromJson(message, JsonObject.class);
    }
}
