package com.traderepublic.quotessystem.rest.services;

import com.traderepublic.quotessystem.data.MongodbClient;
import com.traderepublic.quotessystem.data.Quote;
import com.traderepublic.quotessystem.rest.data.Candlestick;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    @Value("${candlestick.length:60}")
    private Integer candlestickLength;

    @Autowired
    private MongodbClient mongodbClient;

    public List<Candlestick> candlestickInterval(String isin, Instant from, Instant to) {
        List<Candlestick> result = new ArrayList<>();

        List<Quote> quotes = mongodbClient.findQuotesBetween(isin, from, to);

        if(quotes.isEmpty())
            return result;

        Instant partialTo = from.plusSeconds(candlestickLength);
        Instant partialFrom = from;

        Candlestick previousCandlestick = new Candlestick(partialFrom, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, quotes.get(0).getPrice(), partialTo);

        while (partialTo.isBefore(to) || partialTo.compareTo(to) == 0) {
            final Instant quotesFrom = Instant.ofEpochMilli(partialFrom.toEpochMilli());
            final Instant quotesTo = Instant.ofEpochMilli(partialTo.toEpochMilli());
            List<Quote> partialQuotes = quotes.stream()
                    .filter(quote -> quote.getTimestamp().isAfter(quotesFrom) || quote.getTimestamp().compareTo(quotesFrom) == 0)
                    .filter(quote -> quote.getTimestamp().isBefore(quotesTo))
                    .sorted(Comparator.comparing(Quote::getTimestamp))
                    .collect(Collectors.toList());

            BigDecimalSummaryStatistics stats = partialQuotes.stream()
                    .collect(Collectors2.summarizingBigDecimal(Quote::getPrice));

            Candlestick currentCandlestick = new Candlestick(partialFrom,
                    previousCandlestick.getOpenPrice(),
                    previousCandlestick.getHighPrice(),
                    previousCandlestick.getLowPrice(),
                    previousCandlestick.getClosePrice(),
                    partialTo);

            if (stats.getCount() > 1) {
                currentCandlestick = new Candlestick(partialFrom,
                        partialFrom.compareTo(partialQuotes.get(0).getTimestamp()) == 0 ? partialQuotes.get(0).getPrice() : previousCandlestick.getClosePrice(),
                        stats.getMax(),
                        stats.getMin(),
                        partialQuotes.get(partialQuotes.size() - 1).getPrice(),
                        partialTo);
            }
            result.add(currentCandlestick);
            previousCandlestick = currentCandlestick;
            partialFrom = partialTo;
            partialTo = partialTo.plusSeconds(candlestickLength);
        }


        return result;
    }

}
