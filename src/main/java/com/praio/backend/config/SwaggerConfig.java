package com.praio.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI praioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PRAIÔ API")
                        .description("Backend do app PRAIÔ — monitoramento de qualidade de praias brasileiras")
                        .version("v1.0.0"));
    }
}
