package com.freightcomparator.carrier.interfaces.web;

import com.freightcomparator.carrier.application.ListCarriersUseCase;
import com.freightcomparator.carrier.interfaces.web.dto.CarrierResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carriers")
@Tag(name = "Carriers", description = "Available shipping carriers")
public class CarrierController {

    private final ListCarriersUseCase listCarriersUseCase;

    public CarrierController(ListCarriersUseCase listCarriersUseCase) {
        this.listCarriersUseCase = listCarriersUseCase;
    }

    @GetMapping
    @Operation(summary = "List active carriers")
    public ResponseEntity<List<CarrierResponse>> list() {
        List<CarrierResponse> response = listCarriersUseCase.listActive().stream()
                .map(CarrierResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
}
