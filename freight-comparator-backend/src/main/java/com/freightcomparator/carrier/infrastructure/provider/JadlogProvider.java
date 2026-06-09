package com.freightcomparator.carrier.infrastructure.provider;

import com.freightcomparator.carrier.domain.model.CarrierQuote;
import com.freightcomparator.carrier.domain.model.Modality;
import com.freightcomparator.carrier.domain.model.ShipmentSpec;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JadlogProvider extends AbstractMockCarrierProvider {

    public JadlogProvider() {
        super("JADLOG");
    }

    @Override
    public List<CarrierQuote> quote(ShipmentSpec spec) {
        CarrierQuote pkg = new CarrierQuote(
                carrierCode(),
                "Package",
                Modality.ECONOMY,
                price(12.0, 3.0, 0.11, 0.008, spec),
                deliveryDays(2, 18.0, spec));
        return List.of(pkg);
    }
}
