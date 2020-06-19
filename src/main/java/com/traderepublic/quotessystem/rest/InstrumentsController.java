package com.traderepublic.quotessystem.rest;

import com.traderepublic.quotessystem.rest.data.PricedInstrument;
import com.traderepublic.quotessystem.services.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InstrumentsController {

    @Autowired
    private InstrumentService instrumentService;


    @GetMapping("/instruments")
    public List<PricedInstrument> getInstruments() {
        return instrumentService.instrumentsUpdatedPrice();
    }
}
