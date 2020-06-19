package com.traderepublic.quotessystem.rest.services;

import com.traderepublic.quotessystem.data.Instrument;
import com.traderepublic.quotessystem.data.MongodbClient;
import com.traderepublic.quotessystem.rest.data.PricedInstrument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class InstrumentServiceTest {

    @InjectMocks
    private InstrumentService instrumentService;

    @Mock
    private MongodbClient mongodbClient;

    @BeforeEach
    private void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void instrumentsUpdatedPriceTest() {
        // given
        Mockito.when(mongodbClient.findAllInstruments()).thenReturn(allInstrumentsSample());
        Mockito.when(mongodbClient.findLastPrice("1")).thenReturn(BigDecimal.valueOf(10));
        Mockito.when(mongodbClient.findLastPrice("2")).thenReturn(BigDecimal.valueOf(20));
        Mockito.when(mongodbClient.findLastPrice("3")).thenReturn(BigDecimal.valueOf(30));

        List<PricedInstrument> expectedPricedInstruments = List.of(new PricedInstrument("1", "description 1", BigDecimal.valueOf(10)),
                new PricedInstrument("2", "description 2", BigDecimal.valueOf(20)),
                new PricedInstrument("3", "description 3", BigDecimal.valueOf(30)));

        // when
        List<PricedInstrument> result = instrumentService.instrumentsUpdatedPrice();

        // then
        Assertions.assertEquals(expectedPricedInstruments, result);
    }

    private List<Instrument> allInstrumentsSample() {
        return List.of(new Instrument("1", "description 1", Instant.now()),
                new Instrument("2", "description 2", Instant.now()),
                new Instrument("3", "description 3", Instant.now()));
    }
}
