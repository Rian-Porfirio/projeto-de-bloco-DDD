package com.freightcomparator.freight.infrastructure;

import com.freightcomparator.freight.application.port.out.AddressResolverPort;
import com.freightcomparator.freight.application.port.out.ResolvedLocation;
import com.freightcomparator.freight.domain.exception.UnresolvableLocationException;
import com.freightcomparator.freight.domain.model.PostalCode;
import com.freightcomparator.location.domain.model.Address;
import com.freightcomparator.location.domain.model.ZipCode;
import com.freightcomparator.location.domain.port.AddressLookupPort;
import org.springframework.stereotype.Component;

@Component
public class LocationAddressResolverAdapter implements AddressResolverPort {

    private final AddressLookupPort addressLookupPort;

    public LocationAddressResolverAdapter(AddressLookupPort addressLookupPort) {
        this.addressLookupPort = addressLookupPort;
    }

    @Override
    public ResolvedLocation resolve(PostalCode postalCode) {
        ZipCode zipCode = ZipCode.of(postalCode.value());
        Address address = addressLookupPort.findByZipCode(zipCode)
                .orElseThrow(() -> new UnresolvableLocationException(
                        "Could not resolve location for zip code " + postalCode.formatted()));
        return new ResolvedLocation(
                address.zipCode().formatted(),
                address.city(),
                address.state());
    }
}
