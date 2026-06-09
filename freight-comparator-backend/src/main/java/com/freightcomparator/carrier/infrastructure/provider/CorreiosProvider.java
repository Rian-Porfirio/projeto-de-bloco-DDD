package com.freightcomparator.carrier.infrastructure.provider;

import com.freightcomparator.carrier.domain.model.CarrierQuote;
import com.freightcomparator.carrier.domain.model.Modality;
import com.freightcomparator.carrier.domain.model.ShipmentSpec;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CorreiosProvider extends AbstractMockCarrierProvider {

    public CorreiosProvider() {
        super("CORREIOS");
    }

    @Override
    public List<CarrierQuote> quote(ShipmentSpec spec) {
        CarrierQuote sedex = new CarrierQuote(
                carrierCode(),
                "SEDEX",
                Modality.EXPRESS,
                price(18.0, 4.2, 0.18, 0.01, spec),
                deliveryDays(1, 25.0, spec));

        CarrierQuote pac = new CarrierQuote(
                carrierCode(),
                "PAC",
                Modality.ECONOMY,
                price(9.5, 2.4, 0.09, 0.006, spec),
                deliveryDays(3, 12.0, spec));

        return List.of(sedex, pac);
    }
}
