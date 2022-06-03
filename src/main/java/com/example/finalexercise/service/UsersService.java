package com.example.finalexercise.service;
import com.example.finalexercise.model.Users;

public interface UsersService {
	
	Users saveUser(Users user);
	void deleteUser(long id);
}