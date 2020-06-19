package com.traderepublic.quotessystem.clients;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.traderepublic.quotessystem.data.Instrument;
import com.traderepublic.quotessystem.data.MongodbClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class InstrumentMessageHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(InstrumentMessageHandler.class);

    @Autowired
    private MongodbClient mongodbClient;

    public void handleMessage(JsonObject instrumentMessage) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Handling of message started for instrument: {}",
                    new Gson().toJson(instrumentMessage));
        }
        Instrument instrument = new Instrument(instrumentMessage.get("data").getAsJsonObject().get("isin").getAsString(),
                instrumentMessage.get("data").getAsJsonObject().get("description").getAsString(),
                Instant.now());

        switch (instrumentMessage.get("type").getAsString()) {
            case "ADD":
                mongodbClient.insertInstrument(instrument);
                break;
            case "DELETE":
                mongodbClient.deleteInstrument(instrument);
                // Delete quotes associated to it in case the isin is reused
                mongodbClient.deleteQuotesByIsin(instrument.getIsin());
                break;
        }
    }

    public JsonObject parseMessage(String message) {
        return new Gson().fromJson(message, JsonObject.class);
    }
}
