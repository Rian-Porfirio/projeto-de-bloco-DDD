package com.freightcomparator.location.domain.port;

import com.freightcomparator.location.domain.model.Address;
import com.freightcomparator.location.domain.model.ZipCode;

import java.util.Optional;

public interface AddressLookupPort {
    Optional<Address> findByZipCode(ZipCode zipCode);
}
