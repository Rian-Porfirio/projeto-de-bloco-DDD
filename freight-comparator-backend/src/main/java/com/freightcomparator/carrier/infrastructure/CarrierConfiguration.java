package com.freightcomparator.carrier.infrastructure;

import com.freightcomparator.carrier.domain.port.CarrierProvider;
import com.freightcomparator.carrier.domain.service.CarrierProviderRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CarrierConfiguration {

    @Bean
    public CarrierProviderRegistry carrierProviderRegistry(List<CarrierProvider> providers) {
        return new CarrierProviderRegistry(providers);
    }
}
