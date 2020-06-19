package com.traderepublic.quotessystem.clients;

import com.google.gson.Gson;
import com.traderepublic.quotessystem.clients.messages.InstrumentMessage;
import com.traderepublic.quotessystem.data.MongodbClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstrumentMessageHandler implements ProvidersMessageHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(InstrumentMessageHandler.class);

    @Autowired
    private MongodbClient mongodbClient;

    public void handleMessage(InstrumentMessage instrumentMessage) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Handling of message started for instrument: {}",
                    new Gson().toJson(instrumentMessage));
        }
        switch (instrumentMessage.getType()) {
            case "ADD":
                mongodbClient.insertInstrument(instrumentMessage);
                break;
            case "DELETE":
                mongodbClient.deleteInstrument(instrumentMessage);
                // Delete quotes associated to it in case the isin is reused
                mongodbClient.deleteQuotesByIsin(instrumentMessage.getData().getIsin());
                break;
        }
    }

    public InstrumentMessage parseMessage(String message) {
        return new Gson().fromJson(message, InstrumentMessage.class);
    }
}
