package com.freightcomparator.carrier.application;

import com.freightcomparator.carrier.application.dto.CarrierSummary;
import com.freightcomparator.carrier.domain.port.CarrierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCarriersService implements ListCarriersUseCase {

    private final CarrierRepository carrierRepository;

    public ListCarriersService(CarrierRepository carrierRepository) {
        this.carrierRepository = carrierRepository;
    }

    @Override
    public List<CarrierSummary> listActive() {
        return carrierRepository.findAllActive().stream()
                .map(carrier -> new CarrierSummary(
                        carrier.code().value(),
                        carrier.name(),
                        carrier.logoUrl(),
                        carrier.active()))
                .toList();
    }
}
