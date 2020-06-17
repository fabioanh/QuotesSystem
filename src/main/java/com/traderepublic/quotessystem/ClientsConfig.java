package com.traderepublic.quotessystem;

import com.traderepublic.quotessystem.clients.ProvidersSessionHandler;
import com.traderepublic.quotessystem.clients.messages.Instrument;
import com.traderepublic.quotessystem.clients.messages.Quote;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ClientsConfig {

    @Bean
    public WebSocketClient quotesClient() {
        return webSocketClient("quotes", new ProvidersSessionHandler<Quote>());
    }

    @Bean
    public WebSocketClient instrumentsClient() {
        return webSocketClient("instruments", new ProvidersSessionHandler<Instrument>());
    }

    private WebSocketClient webSocketClient(String streamId, StompSessionHandler sessionHandler){
//        List<Transport> transports = new ArrayList<>(2);
//        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//        transports.add(new RestTemplateXhrTransport());
//
//        SockJsClient client = new SockJsClient(transports);
//        client.doHandshake(sessionHandler, "ws://example.com:8080/sockjs");

        final WebSocketClient client = new StandardWebSocketClient();

        final WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.connect(String.format("ws://localhost:8080/%s", streamId), sessionHandler);
        return client;
    }
}
