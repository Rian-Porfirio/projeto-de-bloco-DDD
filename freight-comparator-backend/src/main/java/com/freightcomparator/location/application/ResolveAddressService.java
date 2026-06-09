package com.freightcomparator.location.application;

import com.freightcomparator.location.application.dto.AddressResult;
import com.freightcomparator.location.domain.exception.AddressNotFoundException;
import com.freightcomparator.location.domain.model.Address;
import com.freightcomparator.location.domain.model.ZipCode;
import com.freightcomparator.location.domain.port.AddressLookupPort;
import org.springframework.stereotype.Service;

@Service
public class ResolveAddressService implements ResolveAddressUseCase {

    private final AddressLookupPort addressLookupPort;

    public ResolveAddressService(AddressLookupPort addressLookupPort) {
        this.addressLookupPort = addressLookupPort;
    }

    @Override
    public AddressResult resolve(String zipCode) {
        ZipCode code = ZipCode.of(zipCode);
        Address address = addressLookupPort.findByZipCode(code)
                .orElseThrow(() -> new AddressNotFoundException("Address not found for zip code " + code.formatted()));
        return new AddressResult(
                address.zipCode().formatted(),
                address.street(),
                address.neighborhood(),
                address.city(),
                address.state());
    }
}
