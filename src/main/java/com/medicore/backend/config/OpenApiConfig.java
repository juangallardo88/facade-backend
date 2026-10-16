package com.medicore.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI medicoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MediCore API")
                        .description("API REST del backend de gestión médica del proyecto MediCore")
                        .version("1.0.0"));
    }
}
