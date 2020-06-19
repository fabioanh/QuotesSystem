package com.traderepublic.quotessystem.data;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.traderepublic.quotessystem.clients.messages.Instrument;
import com.traderepublic.quotessystem.clients.messages.InstrumentMessage;
import com.traderepublic.quotessystem.clients.messages.QuoteMessage;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongodbClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongodbClient.class);
    public static final String INSTRUMENTS = "instruments";
    public static final String QUOTES = "quotes";

    @Autowired
    private MongoClient client;

    @Value("${mongodb.db:quotesSystem}")
    private String dbName;

    private Gson gson = new Gson();

    public void insertInstrument(InstrumentMessage message) {
        try {
            LOGGER.debug("Inserting instrument with isin {}", message.getData().getIsin());


            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<InstrumentMessage> instruments = database.getCollection(INSTRUMENTS, InstrumentMessage.class);

            if (instruments == null) {
                database.createCollection(INSTRUMENTS);
                instruments = database.getCollection(INSTRUMENTS, InstrumentMessage.class);
            }
            instruments.insertOne(message);
        } catch (Exception e) {
            LOGGER.error("Failure while inserting message into Mongodb", e);
        }
    }

    public void insertQuote(QuoteMessage message) {
        try {
            LOGGER.debug("Inserting quote with isin {}", message.getData().getIsin());

            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<QuoteMessage> quotes = database.getCollection(QUOTES, QuoteMessage.class);

            if (quotes == null) {
                database.createCollection(QUOTES);
                quotes = database.getCollection(QUOTES, QuoteMessage.class);
            }
            quotes.insertOne(message);
        } catch (Exception e) {
            LOGGER.error("Failure while inserting quote into Mongodb", e);
        }
    }

    public void deleteInstrument(InstrumentMessage instrumentMessage) {
        try {
            LOGGER.debug("Deleting instrument with isin {}", instrumentMessage.getData().getIsin());
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<InstrumentMessage> instruments = database.getCollection(INSTRUMENTS, InstrumentMessage.class);
            Bson filter = Filters.eq("isin", instrumentMessage.getData().getIsin());
            instruments.deleteOne(filter);
        }catch (Exception e){
            LOGGER.error("Failure deleting element from Mongodb", e);
        }
    }

    public void deleteQuotesByIsin(String isin){
        try {
            LOGGER.debug("Deleting quotes associated to isin {}", isin);
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<QuoteMessage> instruments = database.getCollection(QUOTES, QuoteMessage.class);
            Bson filter = Filters.eq("isin", isin);
            instruments.deleteMany(filter);
        }catch (Exception e){
            LOGGER.error("Failure deleting element from Mongodb", e);
        }
    }
}
