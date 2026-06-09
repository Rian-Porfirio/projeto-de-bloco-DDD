package com.freightcomparator.freight.domain.model;

import com.freightcomparator.freight.domain.exception.NoQuotesAvailableException;
import com.freightcomparator.freight.domain.service.CostBenefitCalculator;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class FreightCalculation {

    private final UUID id;
    private final Shipment shipment;
    private final List<FreightQuote> quotes;
    private final FreightQuote bestPrice;
    private final FreightQuote fastestDelivery;
    private final FreightQuote bestCostBenefit;
    private final Instant calculatedAt;

    private FreightCalculation(
            UUID id,
            Shipment shipment,
            List<FreightQuote> quotes,
            FreightQuote bestPrice,
            FreightQuote fastestDelivery,
            FreightQuote bestCostBenefit,
            Instant calculatedAt) {
        this.id = id;
        this.shipment = shipment;
        this.quotes = quotes;
        this.bestPrice = bestPrice;
        this.fastestDelivery = fastestDelivery;
        this.bestCostBenefit = bestCostBenefit;
        this.calculatedAt = calculatedAt;
    }

    public static FreightCalculation create(
            Shipment shipment,
            List<FreightQuote> quotes,
            CostBenefitCalculator costBenefitCalculator) {
        if (shipment == null) {
            throw new IllegalArgumentException("Shipment must not be null");
        }
        if (quotes == null || quotes.isEmpty()) {
            throw new NoQuotesAvailableException("No freight quotes were produced for the shipment");
        }

        List<FreightQuote> ordered = List.copyOf(quotes);
        FreightQuote bestPrice = ordered.stream()
                .min(Comparator.comparing((FreightQuote quote) -> quote.price().amount())
                        .thenComparingInt(FreightQuote::deliveryDays))
                .orElseThrow();
        FreightQuote fastestDelivery = ordered.stream()
                .min(Comparator.comparingInt(FreightQuote::deliveryDays)
                        .thenComparing(quote -> quote.price().amount()))
                .orElseThrow();
        FreightQuote bestCostBenefit = costBenefitCalculator.select(ordered);

        return new FreightCalculation(
                UUID.randomUUID(),
                shipment,
                ordered,
                bestPrice,
                fastestDelivery,
                bestCostBenefit,
                Instant.now());
    }

    public static FreightCalculation restore(
            UUID id,
            Shipment shipment,
            List<FreightQuote> quotes,
            FreightQuote bestPrice,
            FreightQuote fastestDelivery,
            FreightQuote bestCostBenefit,
            Instant calculatedAt) {
        return new FreightCalculation(
                id,
                shipment,
                List.copyOf(quotes),
                bestPrice,
                fastestDelivery,
                bestCostBenefit,
                calculatedAt);
    }

    public boolean isBestPrice(FreightQuote quote) {
        return bestPrice.equals(quote);
    }

    public boolean isFastestDelivery(FreightQuote quote) {
        return fastestDelivery.equals(quote);
    }

    public boolean isBestCostBenefit(FreightQuote quote) {
        return bestCostBenefit.equals(quote);
    }

    public UUID id() {
        return id;
    }

    public Shipment shipment() {
        return shipment;
    }

    public List<FreightQuote> quotes() {
        return quotes;
    }

    public FreightQuote bestPrice() {
        return bestPrice;
    }

    public FreightQuote fastestDelivery() {
        return fastestDelivery;
    }

    public FreightQuote bestCostBenefit() {
        return bestCostBenefit;
    }

    public Instant calculatedAt() {
        return calculatedAt;
    }
}
