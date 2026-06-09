package com.freightcomparator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI freightComparatorOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Freight Comparator API")
                        .description("Freight quotation and comparison service inspired by the Brazilian postal flow")
                        .version("v1")
                        .contact(new Contact().name("Freight Comparator"))
                        .license(new License().name("MIT")));
    }
}
