package com.softvision.masters.tdd.employeeskilltracker;

import com.softvision.masters.tdd.employeeskilltracker.utils.UserDetailsAdapter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.softvision.masters.tdd.employeeskilltracker.mocks.UserMocks.*;

@TestConfiguration
public class SecurityTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                new UserDetailsAdapter(getMockUser1()),
                new UserDetailsAdapter(getMockUser2()));
    }
}
