package com.traderepublic.quotessystem;

import com.traderepublic.quotessystem.clients.ProvidersSessionHandler;
import com.traderepublic.quotessystem.clients.SocketHandler;
import com.traderepublic.quotessystem.clients.messages.Instrument;
import com.traderepublic.quotessystem.clients.messages.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.adapter.ReactorNettyWebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.StandardWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;


@Configuration
public class ClientsConfig {

    private Logger logger = LoggerFactory.getLogger(ClientsConfig.class);

    @Autowired
    private SocketHandler socketHandler;

    @Bean
    public WebSocketClient quotesClient() {
        return webSocketClient("quotes", new ProvidersSessionHandler<Quote>());
    }

    @Bean
    public WebSocketClient instrumentsClient() {
        return webSocketClient("instruments", new ProvidersSessionHandler<Instrument>());
    }

    private WebSocketClient webSocketClient(String streamId, StompSessionHandler sessionHandler) {
        final String url = String.format("ws://localhost:8080/%s", streamId);
        logger.info("Connecting to {}", url);
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(
                URI.create(url),
                session -> session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .doOnNext(message -> logger.info(message))
                        .then()
        ).subscribe();


//        final WebSocketClient client = new StandardWebSocketClient();
//
//        final WebSocketStompClient stompClient = new WebSocketStompClient(client);
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//        stompClient.connect(String.format("ws://localhost:8080/%s", streamId), sessionHandler);
        return client;
    }
}
