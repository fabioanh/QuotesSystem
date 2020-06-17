package com.traderepublic.quotessystem.clients;

import com.traderepublic.quotessystem.clients.messages.Data;
import com.traderepublic.quotessystem.clients.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;


public class ProvidersSessionHandler<T extends Data> extends StompSessionHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(ProvidersSessionHandler.class);


    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/messages", this);
        logger.info("Subscribed to /topic/messages");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), ProvidersSessionHandler.class);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = (Message<T>) payload;
        logger.info("Received : " + msg.getData() + " from : " + msg.getType());
    }
}
