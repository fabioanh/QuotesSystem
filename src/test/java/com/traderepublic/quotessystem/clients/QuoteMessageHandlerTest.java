package com.traderepublic.quotessystem.clients;

import com.traderepublic.quotessystem.clients.messages.Instrument;
import com.traderepublic.quotessystem.clients.messages.Quote;
import org.junit.jupiter.api.Test;

public class QuoteMessageHandlerTest {

    @Test
    public void testQuote(){
        final QuoteMessageHandler messageHandler = new QuoteMessageHandler();
        messageHandler.handleMessage(sampleQuoteMessage());
    }
    private String sampleQuoteMessage(){
        return "{  \"data\": {    \"price\": 957.1053,    \"isin\": \"HG1E56052525\"  },  \"type\": \"QUOTE\"}";
    }

}
