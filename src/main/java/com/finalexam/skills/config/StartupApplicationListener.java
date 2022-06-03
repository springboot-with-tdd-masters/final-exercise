package com.finalexam.skills.config;


import com.finalexam.skills.model.entity.User;
import com.finalexam.skills.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    	user.setPassword(encoder.encode("Welcome123"));
    	
    	userRepository.save(user);
    }
}