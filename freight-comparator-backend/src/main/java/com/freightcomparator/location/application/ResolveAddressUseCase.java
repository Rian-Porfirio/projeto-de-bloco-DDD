package com.freightcomparator.location.application;

import com.freightcomparator.location.application.dto.AddressResult;

public interface ResolveAddressUseCase {
    AddressResult resolve(String zipCode);
}
