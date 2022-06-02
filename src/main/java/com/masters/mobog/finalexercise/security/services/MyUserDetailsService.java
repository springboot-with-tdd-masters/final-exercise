package com.masters.mobog.finalexercise.security.services;

import com.masters.mobog.finalexercise.exceptions.FinalExerciseException;
import com.masters.mobog.finalexercise.exceptions.FinalExerciseExceptionsCode;
import com.masters.mobog.finalexercise.security.entities.User;
import com.masters.mobog.finalexercise.security.entities.UserWrapper;
import com.masters.mobog.finalexercise.security.repositories.UserRepository;
import com.masters.mobog.finalexercise.security.requests.RegisterUserReqDto;
import com.masters.mobog.finalexercise.security.responses.RegisterUserResDto;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile("jwt")
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder bcryptEncoder;

    public MyUserDetailsService(UserRepository repository, PasswordEncoder bcryptEncoder) {
        this.repository = repository;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> found = this.repository.findByUserName(username);
        if (found.isPresent()) {
            User get = found.get();
            return UserWrapper.builder()
                    .userId(get.getUserId())
                    .userName(get.getUserName())
                    .password(get.getPassword())
                    .build();

        }
        throw new UsernameNotFoundException("Not found");
    }

    public RegisterUserResDto registerUser(RegisterUserReqDto userDto) {
        if (!Optional.ofNullable(userDto.getUsername()).isPresent() ||
                !Optional.ofNullable(userDto.getPassword()).isPresent()) {
            throw new FinalExerciseException(FinalExerciseExceptionsCode.JWT_INCOMPLETE_DTLS);
        }
        if (this.repository.findByUserName(userDto.getUsername()).isPresent()) {
            throw new FinalExerciseException(FinalExerciseExceptionsCode.JWT_USER_EXISTS);
        }
        try {
            User newUser = new User();
            newUser.setUserName(userDto.getUsername());
            String password = bcryptEncoder.encode(userDto.getPassword());
            newUser.setPassword(password);

            User saved = this.repository.save(newUser);
            return RegisterUserResDto.builder().username(saved.getUserName()).userId(saved.getUserId()).build();
        } catch (Exception e) {
            throw new FinalExerciseException(FinalExerciseExceptionsCode.JWT_UNABLE_TO_PROCESS);
        }

    }
}
