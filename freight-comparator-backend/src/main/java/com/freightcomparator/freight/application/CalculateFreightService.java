package com.freightcomparator.freight.application;

import com.freightcomparator.freight.application.dto.CalculateFreightCommand;
import com.freightcomparator.freight.application.dto.FreightCalculationResult;
import com.freightcomparator.freight.application.port.in.CalculateFreightUseCase;
import com.freightcomparator.freight.application.port.out.AddressResolverPort;
import com.freightcomparator.freight.application.port.out.RateQuote;
import com.freightcomparator.freight.application.port.out.ShippingRateProviderPort;
import com.freightcomparator.freight.domain.model.Dimensions;
import com.freightcomparator.freight.domain.model.FreightCalculation;
import com.freightcomparator.freight.domain.model.FreightQuote;
import com.freightcomparator.freight.domain.model.Money;
import com.freightcomparator.freight.domain.model.PostalCode;
import com.freightcomparator.freight.domain.model.Shipment;
import com.freightcomparator.freight.domain.model.Weight;
import com.freightcomparator.freight.domain.port.FreightCalculationRepository;
import com.freightcomparator.freight.domain.service.CostBenefitCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CalculateFreightService implements CalculateFreightUseCase {

    private final AddressResolverPort addressResolverPort;
    private final ShippingRateProviderPort shippingRateProviderPort;
    private final FreightCalculationRepository freightCalculationRepository;
    private final CostBenefitCalculator costBenefitCalculator;

    public CalculateFreightService(
            AddressResolverPort addressResolverPort,
            ShippingRateProviderPort shippingRateProviderPort,
            FreightCalculationRepository freightCalculationRepository,
            CostBenefitCalculator costBenefitCalculator) {
        this.addressResolverPort = addressResolverPort;
        this.shippingRateProviderPort = shippingRateProviderPort;
        this.freightCalculationRepository = freightCalculationRepository;
        this.costBenefitCalculator = costBenefitCalculator;
    }

    @Override
    @Transactional
    public FreightCalculationResult calculate(CalculateFreightCommand command) {
        PostalCode origin = PostalCode.of(command.originZipCode());
        PostalCode destination = PostalCode.of(command.destinationZipCode());

        addressResolverPort.resolve(origin);
        addressResolverPort.resolve(destination);

        Shipment shipment = new Shipment(
                origin,
                destination,
                Weight.ofKilograms(command.weight()),
                new Dimensions(command.height(), command.width(), command.length()),
                resolveDeclaredValue(command.declaredValue()));

        List<FreightQuote> quotes = shippingRateProviderPort.quotesFor(shipment).stream()
                .map(this::toFreightQuote)
                .toList();

        FreightCalculation calculation = FreightCalculation.create(shipment, quotes, costBenefitCalculator);
        freightCalculationRepository.save(calculation);

        return FreightResultAssembler.toResult(calculation);
    }

    private Money resolveDeclaredValue(BigDecimal declaredValue) {
        return Money.of(declaredValue == null ? BigDecimal.ZERO : declaredValue);
    }

    private FreightQuote toFreightQuote(RateQuote rateQuote) {
        return new FreightQuote(
                rateQuote.carrierCode(),
                rateQuote.carrierName(),
                rateQuote.logoUrl(),
                rateQuote.serviceName(),
                rateQuote.modality(),
                Money.of(rateQuote.price()),
                rateQuote.deliveryDays());
    }
}
