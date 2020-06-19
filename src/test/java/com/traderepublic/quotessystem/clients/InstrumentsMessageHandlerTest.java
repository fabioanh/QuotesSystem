package com.traderepublic.quotessystem.clients;

import com.traderepublic.quotessystem.clients.messages.Instrument;
import com.traderepublic.quotessystem.clients.messages.InstrumentMessage;
import com.traderepublic.quotessystem.data.MongodbClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

public class InstrumentsMessageHandlerTest {

    @InjectMocks
    private InstrumentMessageHandler messageHandler = new InstrumentMessageHandler();

    @Mock
    private MongodbClient mongodbClient;

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInstrumentParsing() {
        Instant refTime = Instant.now();
        InstrumentMessage expected = new InstrumentMessage(new Instrument("veri varius explicari", "GR0687573886"), "ADD", null);
        InstrumentMessage response = messageHandler.parseMessage(sampleInstrumentMessage());
        Assertions.assertEquals(expected.getData(), response.getData());
        Assertions.assertEquals(expected.getType(), response.getType());
        Assertions.assertTrue(expected.getTimestamp().isAfter(refTime));
    }

    @Test
    public void testAddMessageHandling() {
        InstrumentMessage message = messageHandler.parseMessage(sampleInstrumentMessage());
        messageHandler.handleMessage(message);
        Mockito.verify(mongodbClient).insertInstrument(message);
    }

    @Test
    public void testDeleteMessageHandling() {
        InstrumentMessage message = messageHandler.parseMessage(deleteInstrumentMessage());
        messageHandler.handleMessage(message);
        Mockito.verify(mongodbClient).deleteInstrument(message);
        Mockito.verify(mongodbClient).deleteQuotesByIsin(message.getData().getIsin());
    }

    private String sampleInstrumentMessage() {
        return "{ \"data\": { \"description\": \"veri varius explicari\", \"isin\": \"GR0687573886\"}, \"type\": \"ADD\"}";
    }

    private String deleteInstrumentMessage() {
        return "{ \"data\": { \"description\": \"veri varius explicari\", \"isin\": \"GR0687573886\"}, \"type\": \"DELETE\"}";
    }

}
