package com.freightcomparator.carrier.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarrierJpaRepository extends JpaRepository<CarrierJpaEntity, UUID> {
    List<CarrierJpaEntity> findByActiveTrue();

    Optional<CarrierJpaEntity> findByCode(String code);
}
