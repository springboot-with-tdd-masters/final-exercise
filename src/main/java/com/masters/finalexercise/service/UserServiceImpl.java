package com.masters.finalexercise.service;

import com.masters.finalexercise.exceptions.RecordNotFoundException;
import com.masters.finalexercise.model.UserEntity;
import com.masters.finalexercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<UserEntity> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return userRepository.findAll(paging);
    }

    @Override
    public UserEntity findById(Long id) throws RecordNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(obj -> {
          return obj;
        }).orElseThrow(() -> new RecordNotFoundException("No Record found with id: " + id));
    }

    @Override
    public UserEntity save(UserEntity entity) {
        Optional<UserEntity> user = null;
        if(entity.getId() != null) {
            user = userRepository.findById(entity.getId());
        }

        if(user != null) {
            UserEntity userEntity = user.get();
            userEntity.setUsername(entity.getUsername());
            userEntity.setPassword(passwordEncoder.encode(entity.getPassword()));
            return userRepository.save(userEntity);
        } else {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            return userRepository.save(entity);
        }

    }

    @Override
    public UserEntity delete(Long id) throws RecordNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(obj -> {
            userRepository.deleteById(id);
            return obj;
        }).orElseThrow(() -> new RecordNotFoundException("No Record found with id: " + id));
    }
}
