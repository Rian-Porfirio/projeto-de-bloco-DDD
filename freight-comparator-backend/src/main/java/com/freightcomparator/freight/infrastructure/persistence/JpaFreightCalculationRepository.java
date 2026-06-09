package com.freightcomparator.freight.infrastructure.persistence;

import com.freightcomparator.freight.domain.model.Dimensions;
import com.freightcomparator.freight.domain.model.FreightCalculation;
import com.freightcomparator.freight.domain.model.FreightQuote;
import com.freightcomparator.freight.domain.model.Money;
import com.freightcomparator.freight.domain.model.PostalCode;
import com.freightcomparator.freight.domain.model.Shipment;
import com.freightcomparator.freight.domain.model.Weight;
import com.freightcomparator.freight.domain.port.FreightCalculationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class JpaFreightCalculationRepository implements FreightCalculationRepository {

    private final FreightCalculationJpaRepository jpaRepository;

    public JpaFreightCalculationRepository(FreightCalculationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(FreightCalculation calculation) {
        Shipment shipment = calculation.shipment();
        FreightCalculationJpaEntity entity = new FreightCalculationJpaEntity(
                calculation.id(),
                shipment.origin().value(),
                shipment.destination().value(),
                shipment.weight().kilograms(),
                shipment.dimensions().heightCm(),
                shipment.dimensions().widthCm(),
                shipment.dimensions().lengthCm(),
                shipment.declaredValue().amount(),
                calculation.calculatedAt());

        for (FreightQuote quote : calculation.quotes()) {
            entity.addOption(new FreightCalculationOptionJpaEntity(
                    UUID.randomUUID(),
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
                    calculation.isBestCostBenefit(quote)));
        }

        jpaRepository.save(entity);
    }

    @Override
    public List<FreightCalculation> findRecent(int limit) {
        return jpaRepository.findAllByOrderByCalculatedAtDesc(PageRequest.of(0, limit)).stream()
                .map(this::toDomain)
                .toList();
    }

    private FreightCalculation toDomain(FreightCalculationJpaEntity entity) {
        Shipment shipment = new Shipment(
                PostalCode.of(entity.getOriginZipCode()),
                PostalCode.of(entity.getDestinationZipCode()),
                Weight.ofKilograms(entity.getWeightKg()),
                new Dimensions(entity.getHeightCm(), entity.getWidthCm(), entity.getLengthCm()),
                Money.of(entity.getDeclaredValue()));

        List<FreightQuote> quotes = new ArrayList<>();
        FreightQuote bestPrice = null;
        FreightQuote fastestDelivery = null;
        FreightQuote bestCostBenefit = null;

        for (FreightCalculationOptionJpaEntity option : entity.getOptions()) {
            FreightQuote quote = new FreightQuote(
                    option.getCarrierCode(),
                    option.getCarrierName(),
                    option.getLogoUrl(),
                    option.getServiceName(),
                    option.getModality(),
                    Money.of(option.getPrice(), option.getCurrency()),
                    option.getDeliveryDays());
            quotes.add(quote);
            if (option.isBestPrice()) {
                bestPrice = quote;
            }
            if (option.isFastestDelivery()) {
                fastestDelivery = quote;
            }
            if (option.isBestCostBenefit()) {
                bestCostBenefit = quote;
            }
        }

        return FreightCalculation.restore(
                entity.getId(),
                shipment,
                quotes,
                bestPrice,
                fastestDelivery,
                bestCostBenefit,
                entity.getCalculatedAt());
    }
}
