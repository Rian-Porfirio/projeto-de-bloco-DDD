package com.freightcomparator.carrier.infrastructure.provider;

import com.freightcomparator.carrier.domain.model.CarrierQuote;
import com.freightcomparator.carrier.domain.model.Modality;
import com.freightcomparator.carrier.domain.model.ShipmentSpec;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AzulCargoProvider extends AbstractMockCarrierProvider {

    public AzulCargoProvider() {
        super("AZULCARGO");
    }

    @Override
    public List<CarrierQuote> quote(ShipmentSpec spec) {
        CarrierQuote express = new CarrierQuote(
                carrierCode(),
                "Expresso",
                Modality.AIR,
                price(20.0, 4.5, 0.20, 0.012, spec),
                deliveryDays(1, 30.0, spec));
        return List.of(express);
    }
}
