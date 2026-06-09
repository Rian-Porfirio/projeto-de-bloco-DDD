package com.freightcomparator.carrier.infrastructure.provider;

import com.freightcomparator.carrier.domain.model.CarrierQuote;
import com.freightcomparator.carrier.domain.model.Modality;
import com.freightcomparator.carrier.domain.model.ShipmentSpec;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BraspressProvider extends AbstractMockCarrierProvider {

    public BraspressProvider() {
        super("BRASPRESS");
    }

    @Override
    public List<CarrierQuote> quote(ShipmentSpec spec) {
        CarrierQuote road = new CarrierQuote(
                carrierCode(),
                "Rodoviario",
                Modality.ROAD,
                price(16.0, 3.6, 0.085, 0.005, spec),
                deliveryDays(2, 15.0, spec));
        return List.of(road);
    }
}
