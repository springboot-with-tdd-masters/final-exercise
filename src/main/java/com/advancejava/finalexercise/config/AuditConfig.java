package com.advancejava.finalexercise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "aware")
public class AuditConfig {
//TODO: remove hardcoded user and get from provider
    @Bean
    public AuditorAware<String> aware() {
        return () -> Optional.of("user");
    }
}