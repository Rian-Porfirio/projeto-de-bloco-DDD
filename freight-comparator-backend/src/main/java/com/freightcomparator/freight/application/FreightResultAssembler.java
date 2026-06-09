package com.freightcomparator.freight.application;

import com.freightcomparator.freight.application.dto.FreightCalculationResult;
import com.freightcomparator.freight.application.dto.FreightOption;
import com.freightcomparator.freight.domain.model.FreightCalculation;
import com.freightcomparator.freight.domain.model.FreightQuote;

import java.util.List;

final class FreightResultAssembler {

    private FreightResultAssembler() {
    }

    static FreightCalculationResult toResult(FreightCalculation calculation) {
        List<FreightOption> options = calculation.quotes().stream()
                .map(quote -> toOption(calculation, quote))
                .toList();

        return new FreightCalculationResult(
                calculation.id().toString(),
                calculation.shipment().origin().formatted(),
                calculation.shipment().destination().formatted(),
                toOption(calculation, calculation.bestPrice()),
                toOption(calculation, calculation.fastestDelivery()),
                toOption(calculation, calculation.bestCostBenefit()),
                options,
                calculation.calculatedAt());
    }

    private static FreightOption toOption(FreightCalculation calculation, FreightQuote quote) {
        return new FreightOption(
                quote.carrierCode(),
                quote.carrierName(),
                quote.logoUrl(),
                quote.serviceName(),
                quote.modality(),
                quote.price().amount(),
                quote.price().currency(),
                quote.deliveryDays(),
                calculation.isBestPrice(quote),
                calculation.isFastestDelivery(quote),
                calculation.isBestCostBenefit(quote));
    }
}
