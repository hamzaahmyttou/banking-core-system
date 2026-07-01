package com.banking_core_system.banking_core_system.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bankingOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Banking Core System")

                        .description("""
                                RESTful Banking API built with Spring Boot.
                                
                                Features:
                                - Customer Management
                                - Account Management
                                - Deposit
                                - Withdrawal
                                - Transfer
                                - Concurrency Management
                                """)

                        .version("1.0.0")

                        .contact(new Contact()
                                .name("Hamza AHMYTTOU"))

                        .license(new License()
                                .name("MIT")))

                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/hamzaahmyttou/banking-core-system"));
    }
}
