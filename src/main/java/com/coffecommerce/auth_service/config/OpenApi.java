package com.coffecommerce.auth_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApi {
    // OpenAPI configuration can be added here if needed
    // For example, you can configure the OpenAPI documentation properties
    // using the @Bean annotation to create an OpenAPI bean.

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CoffeeCommerce Auth API")
                        .version("v1.0")
                        .description("Bu dokümantasyon CoffeeCommerce'in kimlik doğrulama servisine aittir.")
                        .contact(new Contact()
                                .name("Berat Kulcu")
                                .email("klcberat13@gmail.com")
                                .url("https://github.com/beratkulcu"))
                        .license(new License().name("CoffeeCommerce Internal License")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server")
                ));
    }
}
