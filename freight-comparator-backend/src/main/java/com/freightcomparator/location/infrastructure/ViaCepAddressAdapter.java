package com.freightcomparator.location.infrastructure;

import com.freightcomparator.location.domain.model.Address;
import com.freightcomparator.location.domain.model.ZipCode;
import com.freightcomparator.location.domain.port.AddressLookupPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
@ConditionalOnProperty(name = "location.provider", havingValue = "viacep")
public class ViaCepAddressAdapter implements AddressLookupPort {

    private final RestClient restClient;

    public ViaCepAddressAdapter(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://viacep.com.br/ws").build();
    }

    @Override
    public Optional<Address> findByZipCode(ZipCode zipCode) {
        ViaCepResponse response = restClient.get()
                .uri("/{cep}/json/", zipCode.value())
                .retrieve()
                .body(ViaCepResponse.class);

        if (response == null || Boolean.TRUE.equals(response.erro())) {
            return Optional.empty();
        }
        return Optional.of(new Address(
                zipCode,
                blankToDash(response.logradouro()),
                blankToDash(response.bairro()),
                response.localidade(),
                response.uf()));
    }

    private String blankToDash(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }

    private record ViaCepResponse(
            String logradouro,
            String bairro,
            String localidade,
            String uf,
            Boolean erro) {
    }
}
