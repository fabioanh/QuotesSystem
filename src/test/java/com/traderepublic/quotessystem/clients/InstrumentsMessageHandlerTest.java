package com.traderepublic.quotessystem.clients;

import org.junit.jupiter.api.Test;

public class InstrumentsMessageHandlerTest {

    @Test
    public void testInstrument(){
        final InstrumentMessageHandler messageHandler = new InstrumentMessageHandler();
        messageHandler.handleMessage(sampleInstrumentMessage());

    }

    private String sampleInstrumentMessage(){
        return "{ \"data\": { \"description\": \"veri varius explicari\", \"isin\": \"GR0687573886\"}, \"type\": \"ADD\"}";
    }

}
