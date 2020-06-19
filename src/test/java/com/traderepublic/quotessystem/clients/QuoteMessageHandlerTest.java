package com.traderepublic.quotessystem.clients;

import com.traderepublic.quotessystem.clients.messages.InstrumentMessage;
import com.traderepublic.quotessystem.clients.messages.Quote;
import com.traderepublic.quotessystem.clients.messages.QuoteMessage;
import com.traderepublic.quotessystem.data.MongodbClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;

public class QuoteMessageHandlerTest {
    @InjectMocks
    private QuoteMessageHandler messageHandler = new QuoteMessageHandler();

    @Mock
    private MongodbClient mongodbClient;

    @BeforeEach
    private void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQuoteParsing() {
        Instant refTime = Instant.now();
        QuoteMessage expected = new QuoteMessage(new Quote(new BigDecimal("957.1053"), "HG1E56052525"), "QUOTE", null);
        QuoteMessage response = messageHandler.parseMessage(sampleQuoteMessage());
        Assertions.assertEquals(expected.getData(), response.getData());
        Assertions.assertEquals(expected.getType(), response.getType());
        Assertions.assertTrue(expected.getTimestamp().isAfter(refTime));
    }

    @Test
    public void testMessageHandling() {
        QuoteMessage message = messageHandler.parseMessage(sampleQuoteMessage());
        messageHandler.handleMessage(message);
        Mockito.verify(mongodbClient).insertQuote(message);
    }

    private String sampleQuoteMessage() {
        return "{  \"data\": {    \"price\": 957.1053,    \"isin\": \"HG1E56052525\"  },  \"type\": \"QUOTE\"}";
    }

}
