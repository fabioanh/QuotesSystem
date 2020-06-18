package com.traderepublic.quotessystem.clients;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.traderepublic.quotessystem.clients.messages.Instrument;
import com.traderepublic.quotessystem.clients.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class InstrumentMessageHandler implements ProvidersMessageHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(InstrumentMessageHandler.class);

    @Override
    public void handleMessage(String message) {
        //TODO: Implement further handling
    }

    @Override
    public Message<Instrument> parseMessage(String message) {

        return new Gson().fromJson(message, new TypeToken<Message<Instrument>>(){}.getType());
    }
}
