package com.traderepublic.quotessystem.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.annotation.PostConstruct;
import java.util.Scanner;

//@Component
public class ProvidersClient {

    private Logger logger = LoggerFactory.getLogger(ProvidersClient.class);

    @Value("${providers.url:ws://localhost:8080}")
    private String providersURL;

    private WebSocketClient client = new StandardWebSocketClient();
    private WebSocketStompClient stompClient = new WebSocketStompClient(client);

    @PostConstruct
    private void setUp(){
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new ProvidersSessionHandler();
        stompClient.connect(providersURL, sessionHandler);
        logger.info("ProvidersClient created successfully");
    }
}
