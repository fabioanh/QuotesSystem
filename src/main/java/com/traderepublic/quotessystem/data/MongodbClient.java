package com.traderepublic.quotessystem.data;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

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

    private MongoDatabase database;
    private MongoCollection<Instrument> instruments;
    private MongoCollection<Quote> quotes;

    @PostConstruct
    private void init() {
        database = client.getDatabase(dbName);
        instruments = database.getCollection(INSTRUMENTS, Instrument.class);
        quotes = database.getCollection(QUOTES, Quote.class);

        if (instruments == null) {
            database.createCollection(INSTRUMENTS);
            instruments = database.getCollection(INSTRUMENTS, Instrument.class);
        }

        if (quotes == null) {
            database.createCollection(QUOTES);
            quotes = database.getCollection(QUOTES, Quote.class);
        }
    }

    public void insertInstrument(Instrument instrument) {
        try {
            LOGGER.debug("Inserting instrument with isin {}", instrument.getIsin());
            Bson filter = eq("isin", instrument.getIsin());
            // If instrument found with given ISIN replace it (this in case the app restarts and db is not fresh)
            // Consider removing it as it adds an overhead to each insertion (although instrument insertion is not so frequent)
            if(! instruments.find(filter).into(new ArrayList<>()).isEmpty()){
                instruments.findOneAndReplace(filter, instrument);
            }else {
                instruments.insertOne(instrument);
            }
        } catch (Exception e) {
            LOGGER.error("Failure while inserting message into Mongodb", e);
        }
    }

    public void insertQuote(Quote message) {
        try {
            LOGGER.debug("Inserting quote with isin {}", message.getIsin());
            quotes.insertOne(message);
        } catch (Exception e) {
            LOGGER.error("Failure while inserting quote into Mongodb", e);
        }
    }

    public void deleteInstrument(Instrument instrument) {
        try {
            LOGGER.debug("Deleting instrument with isin {}", instrument.getIsin());
            Bson filter = eq("isin", instrument.getIsin());
            instruments.deleteOne(filter);
        } catch (Exception e) {
            LOGGER.error("Failure deleting element from Mongodb", e);
        }
    }

    public void deleteQuotesByIsin(String isin) {
        try {
            LOGGER.debug("Deleting quotes associated to isin {}", isin);
            Bson filter = eq("isin", isin);
            quotes.deleteMany(filter);
        } catch (Exception e) {
            LOGGER.error("Failure deleting element from Mongodb", e);
        }
    }

    public List<Instrument> findAllInstruments() {
        List<Instrument> result = new ArrayList<>();
        instruments.find().cursor().forEachRemaining(result::add);
        return result;
    }

    public BigDecimal findLastPrice(String isin) {
        Quote quote = quotes.find(eq("isin", isin))
                .sort(Sorts.descending("timestamp"))
                .limit(1).first();
        return quote != null ? quote.getPrice() : null;
    }

    public List<Quote> findQuotesBetween(String isin, Instant from, Instant to){
        return quotes.find(
                and(
                        eq("isin", isin),
                        gte("timestamp", from),
                        lt("timestamp", to)
                ))
                .sort(Sorts.ascending("timestamp"))
                .into(new ArrayList<>());
    }
}
