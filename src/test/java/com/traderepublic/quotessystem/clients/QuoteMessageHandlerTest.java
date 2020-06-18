package com.traderepublic.quotessystem.clients;

import com.traderepublic.quotessystem.clients.messages.Instrument;
import com.traderepublic.quotessystem.clients.messages.Message;
import com.traderepublic.quotessystem.clients.messages.Quote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class QuoteMessageHandlerTest {

    @Test
    public void testQuoteParsing(){
        final QuoteMessageHandler messageHandler = new QuoteMessageHandler();
        Message<Quote> expected = new Message<>(new Quote(new BigDecimal("957.1053"), "HG1E56052525"), "QUOTE");
        Message<Instrument> response = messageHandler.parseMessage(sampleQuoteMessage());
        Assertions.assertEquals(expected.getData(), response.getData());
        Assertions.assertEquals(expected.getType(), response.getType());
        Assertions.assertNotNull(expected.getDate());
    }
    
    private String sampleQuoteMessage(){
        return "{  \"data\": {    \"price\": 957.1053,    \"isin\": \"HG1E56052525\"  },  \"type\": \"QUOTE\"}";
    }

}
