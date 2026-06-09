package com.freightcomparator.freight.infrastructure.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FreightCalculationJpaRepository extends JpaRepository<FreightCalculationJpaEntity, UUID> {
    List<FreightCalculationJpaEntity> findAllByOrderByCalculatedAtDesc(Pageable pageable);
}
