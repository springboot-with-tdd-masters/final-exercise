package com.softvision.masters.tdd.employeeskilltracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfigurations {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                //.paths(PathSelectors.ant("/users/**"))
                .paths(PathSelectors.any())
                //.paths(PathSelectors.ant("/skills/**"))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Employees - Skill Tracker",
                "Final Exercise for Advanced Java Masters: Springboot with TDD",
                "1.0",
                null,
                new Contact("@jmgloria07", "https://www.quantumjosh.me", "josh@quantumjosh.me"),
                "GNU GPLv3",
                "https://www.gnu.org/licenses/gpl-3.0.en.html",
                Collections.emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}