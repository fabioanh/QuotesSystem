package com.traderepublic.quotessystem;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.traderepublic.quotessystem.clients.InstrumentMessageHandler;
import com.traderepublic.quotessystem.clients.QuoteMessageHandler;
import org.apache.http.HttpHost;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.StandardWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;


@Configuration
public class ClientsConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientsConfig.class);

    //    @Value("${elasticsearch.host:localhost}")
    @Value("${mongodb.host:localhost}")
    private String host;

    //    @Value("${elasticsearch.port:9200}")
    @Value("${mongodb.port:9200}")
    private String port;

    @Value("${mongodb.uri:mongodb://localhost:27017}")
    private String mongoURI;

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

    @Bean(destroyMethod = "close")
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoURI);
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        return MongoClients.create(clientSettings);
//        return new MongoClient(host, Integer.valueOf(port));

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
