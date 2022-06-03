package com.example.finalexercise.service.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.finalexercise.model.Users;
import com.example.finalexercise.repository.UsersRepository;
import com.example.finalexercise.service.UsersService;

@Service(value = "usersService")
public class UsersServiceImpl implements UserDetailsService, UsersService {
	
	@Autowired
	private UsersRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username and/or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}
	
	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	
	public List<Users> findAll() {
		List<Users> list = new ArrayList<>();
		repository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public Users saveUser(Users user) {
		return repository.save(user);
	}

	@Override
	public void deleteUser(long id) {
		repository.deleteById(id);
	}

}