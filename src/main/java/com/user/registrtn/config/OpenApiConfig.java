package com.user.registrtn.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "UserRegistration" ,
        version = "3.1" , description = "API Documentation for UserRegistration"))
public class OpenApiConfig {
}
