package com.example.demo.service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("User successfully found")
    public void successLoadUser() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        User retrievedUser = userRepository.findByUsername(user.getUsername());
        assertThat(retrievedUser.getUsername(), is(user.getUsername()));
    }

}