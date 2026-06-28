package com.idotpet.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "IdotPet API",
                version = "1.0.0",
                description = "API publica para cadastro, listagem e divulgacao de animais para adocao.",
                contact = @Contact(name = "IdotPet"),
                license = @License(name = "Uso interno do projeto")))
public class OpenApiConfig {
}
