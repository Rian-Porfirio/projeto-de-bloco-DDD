package com.freightcomparator.freight.interfaces.web.dto;

import com.freightcomparator.freight.application.dto.CalculateFreightCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CalculateFreightRequest(
        @NotBlank
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Origin zip code must follow the pattern 00000-000")
        String originZipCode,

        @NotBlank
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Destination zip code must follow the pattern 00000-000")
        String destinationZipCode,

        @NotNull
        @Positive
        BigDecimal weight,

        @NotNull
        @Positive
        BigDecimal height,

        @NotNull
        @Positive
        BigDecimal width,

        @NotNull
        @Positive
        BigDecimal length,

        @PositiveOrZero
        BigDecimal declaredValue
) {
    public CalculateFreightCommand toCommand() {
        return new CalculateFreightCommand(
                originZipCode,
                destinationZipCode,
                weight,
                height,
                width,
                length,
                declaredValue);
    }
}
