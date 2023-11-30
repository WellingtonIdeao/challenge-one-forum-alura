package com.br.alura.forum.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                        .info(new Info()
                                .title("Forum Alura API")
                                .description("API Rest da aplicação do Forum Alura, contendo as funcionalidades de CRUD de tópicos, respostas, usuários e cursos")
                        .contact(new Contact()
                                .name("Time Backend")
                                .email("backend@forum-alura"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://forum-alura/api/licenca")));
    }
}
