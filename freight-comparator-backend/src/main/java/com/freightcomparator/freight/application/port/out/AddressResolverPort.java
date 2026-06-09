package com.freightcomparator.freight.application.port.out;

import com.freightcomparator.freight.domain.model.PostalCode;

public interface AddressResolverPort {
    ResolvedLocation resolve(PostalCode postalCode);
}
