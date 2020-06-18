package com.traderepublic.quotessystem.clients;

import com.traderepublic.quotessystem.clients.messages.Instrument;
import com.traderepublic.quotessystem.clients.messages.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InstrumentsMessageHandlerTest {

    @Test
    public void testInstrumentParsing(){
        final InstrumentMessageHandler messageHandler = new InstrumentMessageHandler();
        Message<Instrument> expected = new Message<>(new Instrument("veri varius explicari", "GR0687573886"), "ADD");
        Message<Instrument> response = messageHandler.parseMessage(sampleInstrumentMessage());
        Assertions.assertEquals(expected.getData(), response.getData());
        Assertions.assertEquals(expected.getType(), response.getType());
        Assertions.assertNotNull(expected.getDate());
    }

    private String sampleInstrumentMessage(){
        return "{ \"data\": { \"description\": \"veri varius explicari\", \"isin\": \"GR0687573886\"}, \"type\": \"ADD\"}";
    }

}
