package com.freightcomparator.freight.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "freight_calculation_option")
public class FreightCalculationOptionJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "carrier_code", nullable = false)
    private String carrierCode;

    @Column(name = "carrier_name", nullable = false)
    private String carrierName;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "modality", nullable = false)
    private String modality;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "delivery_days", nullable = false)
    private int deliveryDays;

    @Column(name = "best_price", nullable = false)
    private boolean bestPrice;

    @Column(name = "fastest_delivery", nullable = false)
    private boolean fastestDelivery;

    @Column(name = "best_cost_benefit", nullable = false)
    private boolean bestCostBenefit;

    protected FreightCalculationOptionJpaEntity() {
    }

    public FreightCalculationOptionJpaEntity(
            UUID id,
            String carrierCode,
            String carrierName,
            String logoUrl,
            String serviceName,
            String modality,
            BigDecimal price,
            String currency,
            int deliveryDays,
            boolean bestPrice,
            boolean fastestDelivery,
            boolean bestCostBenefit) {
        this.id = id;
        this.carrierCode = carrierCode;
        this.carrierName = carrierName;
        this.logoUrl = logoUrl;
        this.serviceName = serviceName;
        this.modality = modality;
        this.price = price;
        this.currency = currency;
        this.deliveryDays = deliveryDays;
        this.bestPrice = bestPrice;
        this.fastestDelivery = fastestDelivery;
        this.bestCostBenefit = bestCostBenefit;
    }

    public UUID getId() {
        return id;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getModality() {
        return modality;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public int getDeliveryDays() {
        return deliveryDays;
    }

    public boolean isBestPrice() {
        return bestPrice;
    }

    public boolean isFastestDelivery() {
        return fastestDelivery;
    }

    public boolean isBestCostBenefit() {
        return bestCostBenefit;
    }
}
