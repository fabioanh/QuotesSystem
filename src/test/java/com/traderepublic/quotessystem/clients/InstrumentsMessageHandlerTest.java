package com.traderepublic.quotessystem.clients;

import com.google.gson.JsonObject;
import com.traderepublic.quotessystem.data.Instrument;
import com.traderepublic.quotessystem.data.MongodbClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;

public class InstrumentsMessageHandlerTest {

    @InjectMocks
    private InstrumentMessageHandler messageHandler = new InstrumentMessageHandler();

    @Mock
    private MongodbClient mongodbClient;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInstrumentParsing() {
        // given
        String expectedDescription = "veri varius explicari";
        String expectedIsin = "GR0687573886";
        String expectedType = "ADD";

        // when
        JsonObject response = messageHandler.parseMessage(sampleInstrumentMessage());

        // then
        Assertions.assertEquals(expectedDescription, response.get("data").getAsJsonObject().get("description").getAsString());
        Assertions.assertEquals(expectedIsin, response.get("data").getAsJsonObject().get("isin").getAsString());
        Assertions.assertEquals(expectedType, response.get("type").getAsString());
    }

    @Test
    public void testAddMessageHandling() {
        JsonObject message = messageHandler.parseMessage(sampleInstrumentMessage());
        Instrument instrument = new Instrument(message.get("data").getAsJsonObject().get("isin").getAsString(),
                message.get("data").getAsJsonObject().get("description").getAsString(),
                Instant.now());

        ArgumentCaptor<Instrument> argument = ArgumentCaptor.forClass(Instrument.class);

        //when
        messageHandler.handleMessage(message);

        //then
        Mockito.verify(mongodbClient).insertInstrument(argument.capture());
        Assertions.assertEquals(instrument.getIsin(), argument.getValue().getIsin());
        Assertions.assertEquals(instrument.getDescription(), argument.getValue().getDescription());

    }

    @Test
    public void testDeleteMessageHandling() {
        // given
        JsonObject message = messageHandler.parseMessage(deleteInstrumentMessage());
        Instrument instrument = new Instrument(message.get("data").getAsJsonObject().get("isin").getAsString(),
                message.get("data").getAsJsonObject().get("description").getAsString(),
                Instant.now());
        ArgumentCaptor<Instrument> argument = ArgumentCaptor.forClass(Instrument.class);

        // when
        messageHandler.handleMessage(message);

        // then
        Mockito.verify(mongodbClient).deleteInstrument(argument.capture());
        Mockito.verify(mongodbClient).deleteQuotesByIsin(instrument.getIsin());
        Assertions.assertEquals(instrument.getDescription(), argument.getValue().getDescription());
        Assertions.assertEquals(instrument.getIsin(), argument.getValue().getIsin());
    }

    private String sampleInstrumentMessage() {
        return "{ \"data\": { \"description\": \"veri varius explicari\", \"isin\": \"GR0687573886\"}, \"type\": \"ADD\"}";
    }

    private String deleteInstrumentMessage() {
        return "{ \"data\": { \"description\": \"veri varius explicari\", \"isin\": \"GR0687573886\"}, \"type\": \"DELETE\"}";
    }

}
