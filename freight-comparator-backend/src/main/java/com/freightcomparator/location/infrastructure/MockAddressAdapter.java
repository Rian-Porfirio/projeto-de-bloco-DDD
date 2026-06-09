package com.freightcomparator.location.infrastructure;

import com.freightcomparator.location.domain.model.Address;
import com.freightcomparator.location.domain.model.ZipCode;
import com.freightcomparator.location.domain.port.AddressLookupPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "location.provider", havingValue = "mock", matchIfMissing = true)
public class MockAddressAdapter implements AddressLookupPort {

    private static final Map<Integer, Address> KNOWN = Map.of(
            1001, address("01001", "Praca da Se", "Se", "Sao Paulo", "SP"),
            30130, address("30130", "Avenida Afonso Pena", "Centro", "Belo Horizonte", "MG"),
            20040, address("20040", "Avenida Rio Branco", "Centro", "Rio de Janeiro", "RJ"),
            80010, address("80010", "Rua das Flores", "Centro", "Curitiba", "PR"),
            40010, address("40010", "Praca da Inglaterra", "Comercio", "Salvador", "BA"),
            70040, address("70040", "Esplanada dos Ministerios", "Zona Civico-Administrativa", "Brasilia", "DF")
    );

    private static Address address(String prefix, String street, String neighborhood, String city, String state) {
        return new Address(ZipCode.of(prefix + "000"), street, neighborhood, city, state);
    }

    @Override
    public Optional<Address> findByZipCode(ZipCode zipCode) {
        Address known = KNOWN.get(zipCode.prefix());
        if (known != null) {
            return Optional.of(new Address(zipCode, known.street(), known.neighborhood(), known.city(), known.state()));
        }
        return Optional.of(new Address(zipCode, "Unknown street", "Unknown", regionCity(zipCode), regionState(zipCode)));
    }

    private String regionCity(ZipCode zipCode) {
        return "City " + (zipCode.prefix() / 1000);
    }

    private String regionState(ZipCode zipCode) {
        int region = zipCode.prefix() / 10000;
        return switch (region) {
            case 0, 1 -> "SP";
            case 2 -> "RJ";
            case 3 -> "MG";
            case 4 -> "BA";
            case 5 -> "PE";
            case 6 -> "CE";
            case 7 -> "DF";
            case 8 -> "PR";
            default -> "RS";
        };
    }
}
