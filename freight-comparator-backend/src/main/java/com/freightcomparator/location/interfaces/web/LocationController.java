package com.freightcomparator.location.interfaces.web;

import com.freightcomparator.location.application.ResolveAddressUseCase;
import com.freightcomparator.location.interfaces.web.dto.AddressResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/locations")
@Tag(name = "Locations", description = "Address resolution and zip code validation")
public class LocationController {

    private final ResolveAddressUseCase resolveAddressUseCase;

    public LocationController(ResolveAddressUseCase resolveAddressUseCase) {
        this.resolveAddressUseCase = resolveAddressUseCase;
    }

    @GetMapping("/{zipCode}")
    @Operation(summary = "Resolve an address by zip code")
    public ResponseEntity<AddressResponse> resolve(@PathVariable String zipCode) {
        return ResponseEntity.ok(AddressResponse.from(resolveAddressUseCase.resolve(zipCode)));
    }
}
