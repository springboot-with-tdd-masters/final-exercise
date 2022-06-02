package com.masters.mobog.finalexercise.security.controllers;

import com.masters.mobog.finalexercise.exceptions.FinalExerciseException;
import com.masters.mobog.finalexercise.exceptions.FinalExerciseExceptionsCode;
import com.masters.mobog.finalexercise.security.entities.UserWrapper;
import com.masters.mobog.finalexercise.security.jwt.JwtTokenUtil;
import com.masters.mobog.finalexercise.security.requests.JwtTokenRequest;
import com.masters.mobog.finalexercise.security.requests.RegisterUserReqDto;
import com.masters.mobog.finalexercise.security.responses.JwtTokenResponse;
import com.masters.mobog.finalexercise.security.responses.RegisterUserResDto;
import com.masters.mobog.finalexercise.security.services.MyUserDetailsService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/jwt")
@Profile("jwt")
public class JwtController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final MyUserDetailsService userDetailsService; // implementation used for custom methods

    public JwtController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, MyUserDetailsService userDetailsService){
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }
    @PostMapping("/authenticate")
    public ResponseEntity<JwtTokenResponse> createAuthenticationToken(@RequestBody JwtTokenRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        final UserWrapper user = (UserWrapper) userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    @PostMapping("/register")
    public RegisterUserResDto registerUser(@RequestBody RegisterUserReqDto user) {
        return this.userDetailsService.registerUser(user);
    }

    private void authenticate(String userName, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new FinalExerciseException(FinalExerciseExceptionsCode.JWT_EXCEPTION);
        }
    }

}
