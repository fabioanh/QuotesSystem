package com.traderepublic.quotessystem;

import com.traderepublic.quotessystem.clients.InstrumentMessageHandler;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientsConfig.class);

    @Autowired
    private InstrumentMessageHandler instrumentMessageHandler;

    @Autowired
    private QuoteMessageHandler quoteMessageHandler;

    @Bean
    public WebSocketClient quotesClient() {
        return webSocketClient("quotes");
    }

    @Bean
    public WebSocketClient instrumentsClient() {
        return webSocketClient("instruments");
    }


    private WebSocketClient webSocketClient(String streamId) {
        final String url = String.format("ws://localhost:8080/%s", streamId);
        LOGGER.info("Connecting to {}", url);
        WebSocketClient client = new StandardWebSocketClient();
        switch (streamId) {
            case "instruments":
                client.execute(
                        URI.create(url),
                        session -> session.receive()
                                .map(WebSocketMessage::getPayloadAsText)
                                .map(instrumentMessageHandler::parseMessage)
                                .doOnNext(instrumentMessageHandler::handleMessage)
                                .then()
                ).subscribe();
                break;
            case "quotes":
                client.execute(
                        URI.create(url),
                        session -> session.receive()
                                .map(WebSocketMessage::getPayloadAsText)
                                .map(quoteMessageHandler::parseMessage)
                                .doOnNext(quoteMessageHandler::handleMessage)
                                .then()
                ).subscribe();
                break;
        }
        return client;
    }
}
