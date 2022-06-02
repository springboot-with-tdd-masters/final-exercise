package com.masters.finalexercise.service;

import com.masters.finalexercise.model.UserEntity;
import com.masters.finalexercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.map(obj -> {
            return new User(obj.getUsername(), obj.getPassword(), new ArrayList<>());
        }).orElseThrow(() -> new UsernameNotFoundException("No Record found with username: " + username));
    }

}
