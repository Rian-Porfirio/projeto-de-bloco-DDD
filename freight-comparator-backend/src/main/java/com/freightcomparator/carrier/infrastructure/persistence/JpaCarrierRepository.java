package com.freightcomparator.carrier.infrastructure.persistence;

import com.freightcomparator.carrier.domain.model.Carrier;
import com.freightcomparator.carrier.domain.model.CarrierCode;
import com.freightcomparator.carrier.domain.port.CarrierRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaCarrierRepository implements CarrierRepository {

    private final CarrierJpaRepository jpaRepository;

    public JpaCarrierRepository(CarrierJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Carrier> findAllActive() {
        return jpaRepository.findByActiveTrue().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Carrier> findByCode(CarrierCode code) {
        return jpaRepository.findByCode(code.value()).map(this::toDomain);
    }

    private Carrier toDomain(CarrierJpaEntity entity) {
        return new Carrier(
                entity.getId(),
                CarrierCode.of(entity.getCode()),
                entity.getName(),
                entity.getLogoUrl(),
                entity.isActive());
    }
}
