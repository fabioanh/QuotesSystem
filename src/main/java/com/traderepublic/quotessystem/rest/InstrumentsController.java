package com.traderepublic.quotessystem.rest;

import com.traderepublic.quotessystem.rest.data.Candlestick;
import com.traderepublic.quotessystem.rest.data.PricedInstrument;
import com.traderepublic.quotessystem.rest.services.InstrumentService;
import com.traderepublic.quotessystem.rest.services.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
public class InstrumentsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentsController.class);

    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    private QuoteService quoteService;


    @GetMapping("/instruments")
    public List<PricedInstrument> getInstruments() {
        return instrumentService.instrumentsUpdatedPrice();
    }

    @GetMapping("/instruments/{isin}/candlesticks")
    public List<Candlestick> getCandlesticks(@PathVariable("isin") String isin) {

        Instant from = Instant.now().minus(30, ChronoUnit.MINUTES);
        Instant to = Instant.now();


        return quoteService.candlestickInterval(isin, from, to);
    }
}
