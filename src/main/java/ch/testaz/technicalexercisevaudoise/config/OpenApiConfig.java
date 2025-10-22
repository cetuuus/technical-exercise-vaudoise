package ch.testaz.technicalexercisevaudoise.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Technical Exercise Vaudoise API")
                        .description("REST API for managing clients and contracts. Auto-generated Swagger documentation via springdoc-openapi.")
                        .version("v1"))
                .servers(List.of(new Server().url("/").description("Default")))
                .externalDocs(new ExternalDocumentation()
                        .description("OpenAPI JSON")
                        .url("/v3/api-docs"));
    }
}
