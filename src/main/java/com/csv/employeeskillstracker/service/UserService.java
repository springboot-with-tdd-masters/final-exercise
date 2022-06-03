package com.csv.employeeskillstracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.csv.employeeskillstracker.model.User;


@Service(value = "userService")
public interface UserService {
	
	public List<User> findAll();
	
	public void delete(long id);
	
	public User save(User user);
}
