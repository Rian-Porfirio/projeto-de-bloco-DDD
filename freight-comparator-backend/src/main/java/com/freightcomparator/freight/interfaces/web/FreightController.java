package com.freightcomparator.freight.interfaces.web;

import com.freightcomparator.freight.application.port.in.CalculateFreightUseCase;
import com.freightcomparator.freight.application.port.in.FreightHistoryUseCase;
import com.freightcomparator.freight.interfaces.web.dto.CalculateFreightRequest;
import com.freightcomparator.freight.interfaces.web.dto.FreightCalculationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/freights")
@Tag(name = "Freights", description = "Freight calculation and comparison")
public class FreightController {

    private final CalculateFreightUseCase calculateFreightUseCase;
    private final FreightHistoryUseCase freightHistoryUseCase;

    public FreightController(
            CalculateFreightUseCase calculateFreightUseCase,
            FreightHistoryUseCase freightHistoryUseCase) {
        this.calculateFreightUseCase = calculateFreightUseCase;
        this.freightHistoryUseCase = freightHistoryUseCase;
    }

    @PostMapping("/calculate")
    @Operation(summary = "Calculate and compare freight options across carriers")
    public ResponseEntity<FreightCalculationResponse> calculate(@Valid @RequestBody CalculateFreightRequest request) {
        FreightCalculationResponse response = FreightCalculationResponse.from(
                calculateFreightUseCase.calculate(request.toCommand()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    @Operation(summary = "List the most recent freight calculations")
    public ResponseEntity<List<FreightCalculationResponse>> history(
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        List<FreightCalculationResponse> response = freightHistoryUseCase.recent(limit).stream()
                .map(FreightCalculationResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
}
