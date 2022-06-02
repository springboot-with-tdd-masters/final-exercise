package com.softvision.masters.tdd.employeeskilltracker.controller;

import com.softvision.masters.tdd.employeeskilltracker.controller.forms.LoginForm;
import com.softvision.masters.tdd.employeeskilltracker.controller.forms.SignupForm;
import com.softvision.masters.tdd.employeeskilltracker.security.jwt.JwtUtils;
import com.softvision.masters.tdd.employeeskilltracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody SignupForm signUpRequest) {
        userService.createUser(
                new SignupForm.UserSignupFormAdapter(signUpRequest)
                        .build()
        );
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(jwtUtils.generateJwt(authentication));
    }
}
