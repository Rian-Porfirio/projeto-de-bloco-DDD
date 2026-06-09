package com.freightcomparator.freight.infrastructure.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "freight_calculation")
public class FreightCalculationJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "origin_zip_code", nullable = false)
    private String originZipCode;

    @Column(name = "destination_zip_code", nullable = false)
    private String destinationZipCode;

    @Column(name = "weight_kg", nullable = false)
    private BigDecimal weightKg;

    @Column(name = "height_cm", nullable = false)
    private BigDecimal heightCm;

    @Column(name = "width_cm", nullable = false)
    private BigDecimal widthCm;

    @Column(name = "length_cm", nullable = false)
    private BigDecimal lengthCm;

    @Column(name = "declared_value", nullable = false)
    private BigDecimal declaredValue;

    @Column(name = "calculated_at", nullable = false)
    private Instant calculatedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "freight_calculation_id", nullable = false)
    private List<FreightCalculationOptionJpaEntity> options = new ArrayList<>();

    protected FreightCalculationJpaEntity() {
    }

    public FreightCalculationJpaEntity(
            UUID id,
            String originZipCode,
            String destinationZipCode,
            BigDecimal weightKg,
            BigDecimal heightCm,
            BigDecimal widthCm,
            BigDecimal lengthCm,
            BigDecimal declaredValue,
            Instant calculatedAt) {
        this.id = id;
        this.originZipCode = originZipCode;
        this.destinationZipCode = destinationZipCode;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.widthCm = widthCm;
        this.lengthCm = lengthCm;
        this.declaredValue = declaredValue;
        this.calculatedAt = calculatedAt;
    }

    public void addOption(FreightCalculationOptionJpaEntity option) {
        this.options.add(option);
    }

    public UUID getId() {
        return id;
    }

    public String getOriginZipCode() {
        return originZipCode;
    }

    public String getDestinationZipCode() {
        return destinationZipCode;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public BigDecimal getHeightCm() {
        return heightCm;
    }

    public BigDecimal getWidthCm() {
        return widthCm;
    }

    public BigDecimal getLengthCm() {
        return lengthCm;
    }

    public BigDecimal getDeclaredValue() {
        return declaredValue;
    }

    public Instant getCalculatedAt() {
        return calculatedAt;
    }

    public List<FreightCalculationOptionJpaEntity> getOptions() {
        return options;
    }
}
