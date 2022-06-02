package com.example.finalexercise.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.finalexercise.model.User;
import com.example.finalexercise.repository.UserRepository;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
    @Override 
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	User user = new User();
    	user.setUsername("admin");
    	user.setPassword(encoder.encode("welcome123"));
    	
    	userRepository.save(user);
    }
}