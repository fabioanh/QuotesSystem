package com.traderepublic.quotessystem.rest.services;

import com.traderepublic.quotessystem.data.Instrument;
import com.traderepublic.quotessystem.data.MongodbClient;
import com.traderepublic.quotessystem.data.Quote;
import com.traderepublic.quotessystem.rest.data.Candlestick;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class QuoteServiceTest {

    @InjectMocks
    private QuoteService quoteService;

    @Mock
    private MongodbClient mongodbClient;

    @BeforeEach
    private void initMocks() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(quoteService, "candlestickLength", 60);
    }

    @Test
    public void candlestickIntervalTest() {
        // given
        String isin = "isin";
        Instant refTime = Instant.ofEpochMilli(1577836800000l);
        Mockito.when(mongodbClient.findQuotesBetween(isin, refTime, refTime.plusSeconds(120))).thenReturn(quoteIntervalSample1(refTime, isin));

        List<Candlestick> expectedCandlesticks = List.of(new Candlestick(refTime, new BigDecimal("3"), new BigDecimal("7"), new BigDecimal("1"), new BigDecimal("5"), refTime.plusSeconds(60)),
                new Candlestick(refTime.plusSeconds(60), new BigDecimal("5"), new BigDecimal("9"), new BigDecimal("2"), new BigDecimal("8"), refTime.plusSeconds(120)));

        // when
        List<Candlestick> result = quoteService.candlestickInterval(isin, refTime, refTime.plusSeconds(120));

        // then
        Assertions.assertEquals(expectedCandlesticks, result);
    }

    @Test
    public void candlestickIntervalTest2() {
        // given
        String isin = "isin";
        Instant refTime = Instant.ofEpochMilli(1577836800000l);
        Mockito.when(mongodbClient.findQuotesBetween(isin, refTime, refTime.plusSeconds(120))).thenReturn(quoteIntervalSample2(refTime, isin));

        List<Candlestick> expectedCandlesticks = List.of(new Candlestick(refTime, new BigDecimal("3"), new BigDecimal("7"), new BigDecimal("1"), new BigDecimal("5"), refTime.plusSeconds(60)),
                new Candlestick(refTime.plusSeconds(60), new BigDecimal("2"), new BigDecimal("9"), new BigDecimal("2"), new BigDecimal("8"), refTime.plusSeconds(120)));

        // when
        List<Candlestick> result = quoteService.candlestickInterval(isin, refTime, refTime.plusSeconds(120));

        // then
        Assertions.assertEquals(expectedCandlesticks, result);
    }

    @Test
    public void candlestickReplicateValuesTest() {
        // given
        String isin = "isin";
        Instant refTime = Instant.ofEpochMilli(1577836800000l);
        Mockito.when(mongodbClient.findQuotesBetween(isin, refTime, refTime.plusSeconds(120))).thenReturn(quoteIntervalSample3(refTime, isin));

        List<Candlestick> expectedCandlesticks = List.of(new Candlestick(refTime, new BigDecimal("3"), new BigDecimal("7"), new BigDecimal("1"), new BigDecimal("5"), refTime.plusSeconds(60)),
                new Candlestick(refTime.plusSeconds(60), new BigDecimal("3"), new BigDecimal("7"), new BigDecimal("1"), new BigDecimal("5"), refTime.plusSeconds(120)));

        // when
        List<Candlestick> result = quoteService.candlestickInterval(isin, refTime, refTime.plusSeconds(120));

        // then
        Assertions.assertEquals(expectedCandlesticks, result);
    }

    private List<Quote> quoteIntervalSample1(Instant refTime, String isin) {
        return List.of(
                new Quote(isin, new BigDecimal("3"),  refTime.plusSeconds(3)),
                new Quote(isin, new BigDecimal("4"),  refTime.plusSeconds(15)),
                new Quote(isin, new BigDecimal("2"),  refTime.plusSeconds(24)),
                new Quote(isin, new BigDecimal("1"),  refTime.plusSeconds(36)),
                new Quote(isin, new BigDecimal("7"),  refTime.plusSeconds(42)),
                new Quote(isin, new BigDecimal("5"),  refTime.plusSeconds(56)),
                new Quote(isin, new BigDecimal("2"),  refTime.plusSeconds(69)),
                new Quote(isin, new BigDecimal("6"),  refTime.plusSeconds(76)),
                new Quote(isin, new BigDecimal("9"),  refTime.plusSeconds(80)),
                new Quote(isin, new BigDecimal("3"),  refTime.plusSeconds(81)),
                new Quote(isin, new BigDecimal("6"),  refTime.plusSeconds(103)),
                new Quote(isin, new BigDecimal("7"),  refTime.plusSeconds(111)),
                new Quote(isin, new BigDecimal("8"),  refTime.plusSeconds(119))
        );
    }
    private List<Quote> quoteIntervalSample2(Instant refTime, String isin) {
        return List.of(
                new Quote(isin, new BigDecimal("3"),  refTime.plusSeconds(3)),
                new Quote(isin, new BigDecimal("4"),  refTime.plusSeconds(15)),
                new Quote(isin, new BigDecimal("2"),  refTime.plusSeconds(24)),
                new Quote(isin, new BigDecimal("1"),  refTime.plusSeconds(36)),
                new Quote(isin, new BigDecimal("7"),  refTime.plusSeconds(42)),
                new Quote(isin, new BigDecimal("5"),  refTime.plusSeconds(56)),
                new Quote(isin, new BigDecimal("2"),  refTime.plusSeconds(60)),
                new Quote(isin, new BigDecimal("6"),  refTime.plusSeconds(76)),
                new Quote(isin, new BigDecimal("9"),  refTime.plusSeconds(80)),
                new Quote(isin, new BigDecimal("3"),  refTime.plusSeconds(81)),
                new Quote(isin, new BigDecimal("6"),  refTime.plusSeconds(103)),
                new Quote(isin, new BigDecimal("7"),  refTime.plusSeconds(111)),
                new Quote(isin, new BigDecimal("8"),  refTime.plusSeconds(119))
        );
    }

    private List<Quote> quoteIntervalSample3(Instant refTime, String isin) {
        return List.of(
                new Quote(isin, new BigDecimal("3"),  refTime.plusSeconds(3)),
                new Quote(isin, new BigDecimal("4"),  refTime.plusSeconds(15)),
                new Quote(isin, new BigDecimal("2"),  refTime.plusSeconds(24)),
                new Quote(isin, new BigDecimal("1"),  refTime.plusSeconds(36)),
                new Quote(isin, new BigDecimal("7"),  refTime.plusSeconds(42)),
                new Quote(isin, new BigDecimal("5"),  refTime.plusSeconds(56))
        );
    }

    private List<Instrument> allInstrumentsSample() {
        return List.of(new Instrument("1", "description 1", Instant.now()),
                new Instrument("2", "description 2", Instant.now()),
                new Instrument("3", "description 3", Instant.now()));
    }
}
