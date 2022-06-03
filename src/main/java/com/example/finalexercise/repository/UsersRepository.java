package com.example.finalexercise.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.finalexercise.model.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

	Users findByUsername(String username);

}
