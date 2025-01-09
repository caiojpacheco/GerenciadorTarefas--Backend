package org.neki.gerenciador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API Gerenciador de Eventos", version = "1.0", description = "Documentação da API Gerenciador de Eventos"))
public class OpenApiConfig {
	@Bean
	public OpenAPI myOpenApi() {
		Contact contact = new Contact();
		contact.setName("Gerenciador");

		License apacheLicense = new License().name("Apache").url("https://opensource.org/licenses/Apache-2.0");

		io.swagger.v3.oas.models.info.Info info = new io.swagger.v3.oas.models.info.Info().title("Gerenciador de Eventos")
				.version("1.0").contact(contact).description("API DO SISTEMA DE GERENCIAMENTO DE EVENTOS")
				.termsOfService("https://www.neki.com.br").license(apacheLicense);
		return new OpenAPI().info(info);
	}
}