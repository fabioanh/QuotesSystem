package com.traderepublic.quotessystem.services;

import com.traderepublic.quotessystem.data.Instrument;
import com.traderepublic.quotessystem.data.MongodbClient;
import com.traderepublic.quotessystem.rest.data.PricedInstrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstrumentService {

    @Autowired
    private MongodbClient mongodbClient;

    /**
     * List of all instruments with the most up to date price
     * @return
     */
    public List<PricedInstrument> instrumentsUpdatedPrice(){
        return mongodbClient.findAllInstruments().stream()
                .map(this::retrieveAndAddPrice)
                .collect(Collectors.toList());
    }

    private PricedInstrument retrieveAndAddPrice(Instrument instrument) {
        BigDecimal price = mongodbClient.findLastPrice(instrument.getIsin());
        return new PricedInstrument(instrument.getIsin(), instrument.getDescription(), price);
    }
}
