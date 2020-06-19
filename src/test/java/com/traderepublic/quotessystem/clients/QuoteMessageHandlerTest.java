package com.traderepublic.quotessystem.clients;

import com.google.gson.JsonObject;
import com.traderepublic.quotessystem.data.MongodbClient;
import com.traderepublic.quotessystem.data.Quote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.Instant;

public class QuoteMessageHandlerTest {
    @InjectMocks
    private QuoteMessageHandler messageHandler = new QuoteMessageHandler();

    @Mock
    private MongodbClient mongodbClient;

    @BeforeEach
    private void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQuoteParsing() {
        // given
        BigDecimal expectedPrice = new BigDecimal("957.1053");
        String expectedIsin = "HG1E56052525";
        String expectedType = "QUOTE";

        // when
        JsonObject response = messageHandler.parseMessage(sampleQuoteMessage());

        // then
        Assertions.assertEquals(expectedPrice, new BigDecimal(response.get("data").getAsJsonObject().get("price").getAsString()));
        Assertions.assertEquals(expectedIsin, response.get("data").getAsJsonObject().get("isin").getAsString());
        ;
        Assertions.assertEquals(expectedType, response.get("type").getAsString());
    }

    @Test
    public void testMessageHandling() {

        // given
        JsonObject message = messageHandler.parseMessage(sampleQuoteMessage());
        Quote quote = new Quote(message.get("data").getAsJsonObject().get("isin").getAsString(),
                new BigDecimal(message.get("data").getAsJsonObject().get("price").getAsString()),
                Instant.now());
        ArgumentCaptor<Quote> argument = ArgumentCaptor.forClass(Quote.class);

        // when
        messageHandler.handleMessage(message);

        // then
        Mockito.verify(mongodbClient).insertQuote(argument.capture());
        Assertions.assertEquals(quote.getIsin(), argument.getValue().getIsin());
        Assertions.assertEquals(quote.getPrice(), argument.getValue().getPrice());
    }

    private String sampleQuoteMessage() {
        return "{  \"data\": {    \"price\": 957.1053,    \"isin\": \"HG1E56052525\"  },  \"type\": \"QUOTE\"}";
    }

}
