package com.softvision.masters.tdd.employeeskilltracker.service;
import com.softvision.masters.tdd.employeeskilltracker.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(User user);
}
