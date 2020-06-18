package com.traderepublic.quotessystem;

import com.traderepublic.quotessystem.clients.InstrumentMessageHandler;
import com.traderepublic.quotessystem.clients.ProvidersMessageHandler;
import com.traderepublic.quotessystem.clients.QuoteMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.StandardWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;


@Configuration
public class ClientsConfig {

    private Logger logger = LoggerFactory.getLogger(ClientsConfig.class);

    @Autowired
    private InstrumentMessageHandler instrumentMessageHandler;

    @Autowired
    private QuoteMessageHandler quoteMessageHandler;

    @Bean
    public WebSocketClient quotesClient() {
        return webSocketClient("quotes", quoteMessageHandler);
    }

    @Bean
    public WebSocketClient instrumentsClient() {
        return webSocketClient("instruments", instrumentMessageHandler);
    }

    private WebSocketClient webSocketClient(String streamId, ProvidersMessageHandler messageHandler) {
        final String url = String.format("ws://localhost:8080/%s", streamId);
        logger.info("Connecting to {}", url);
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(
                URI.create(url),
                session -> session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .doOnNext(messageHandler::handleMessage)
                        .then()
        ).subscribe();
        return client;
    }
}
