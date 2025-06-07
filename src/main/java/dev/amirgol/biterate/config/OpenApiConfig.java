package dev.amirgol.biterate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI biteRateOpenAPI() {
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Development Server");

        Contact contact = new Contact()
                .name("BiteRate Team")
                .url("https://biterate.dev")
                .email("contact@biterate.dev");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("BiteRate API Documentation")
                .version("1.0.0")
                .description("REST API documentation for BiteRate restaurant review platform")
                .contact(contact)
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
