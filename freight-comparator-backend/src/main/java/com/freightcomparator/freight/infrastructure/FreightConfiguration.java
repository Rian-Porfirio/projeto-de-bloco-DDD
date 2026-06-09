package com.freightcomparator.freight.infrastructure;

import com.freightcomparator.freight.domain.service.CostBenefitCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FreightConfiguration {

    @Bean
    public CostBenefitCalculator costBenefitCalculator() {
        return new CostBenefitCalculator();
    }
}
