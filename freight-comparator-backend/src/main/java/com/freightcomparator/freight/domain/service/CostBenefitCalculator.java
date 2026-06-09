package com.freightcomparator.freight.domain.service;

import com.freightcomparator.freight.domain.model.FreightQuote;

import java.util.Comparator;
import java.util.List;

public class CostBenefitCalculator {

    private static final double PRICE_WEIGHT = 0.6;
    private static final double DELIVERY_WEIGHT = 0.4;

    public FreightQuote select(List<FreightQuote> quotes) {
        if (quotes == null || quotes.isEmpty()) {
            throw new IllegalArgumentException("Quotes must not be empty");
        }

        double minPrice = quotes.stream().mapToDouble(q -> q.price().amount().doubleValue()).min().orElse(0);
        double maxPrice = quotes.stream().mapToDouble(q -> q.price().amount().doubleValue()).max().orElse(0);
        int minDays = quotes.stream().mapToInt(FreightQuote::deliveryDays).min().orElse(0);
        int maxDays = quotes.stream().mapToInt(FreightQuote::deliveryDays).max().orElse(0);

        return quotes.stream()
                .min(
                        Comparator.comparingDouble(
                                (FreightQuote quote) ->
                                        score(quote, minPrice, maxPrice, minDays, maxDays)
                        ).thenComparing(
                                quote -> quote.price().amount()
                        )
                )
                .orElseThrow();
    }

    private double score(FreightQuote quote, double minPrice, double maxPrice, int minDays, int maxDays) {
        double priceScore = normalize(quote.price().amount().doubleValue(), minPrice, maxPrice);
        double deliveryScore = normalize(quote.deliveryDays(), minDays, maxDays);
        return (PRICE_WEIGHT * priceScore) + (DELIVERY_WEIGHT * deliveryScore);
    }

    private double normalize(double value, double min, double max) {
        if (max == min) {
            return 0.0;
        }
        return (value - min) / (max - min);
    }
}
