package com.masters.finalexercise.repository;

import com.masters.finalexercise.common.repository.BaseRepository;
import com.masters.finalexercise.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends BaseRepository<UserEntity, Long> {

    public Optional<UserEntity> findByUsername(String username);

}
