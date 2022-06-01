package com.masters.mobog.finalexercise.security.repositories;

import com.masters.mobog.finalexercise.security.entities.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("jwt")
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserName(String username);
}
