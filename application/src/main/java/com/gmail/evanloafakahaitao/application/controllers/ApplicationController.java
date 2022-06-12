package com.gmail.evanloafakahaitao.application.controllers;

import com.gmail.evanloafakahaitao.application.model.AuthRequest;
import com.gmail.evanloafakahaitao.application.model.RegistrationRequest;
import com.gmail.evanloafakahaitao.application.security.JwtProvider;
import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    private static final Logger logger = LogManager.getLogger(ApplicationController.class);

    public ApplicationController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setEmail(registrationRequest.getEmail());
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        User savedUser = userService.save(user);
        logger.info("Saved User {}", savedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authorize(@RequestBody @Valid AuthRequest authRequest) {
        User user = userService.findByEmailAndPassword(
                authRequest.getEmail(),
                authRequest.getPassword()
        );
        String token = jwtProvider.generateToken(user.getEmail());
        return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
    }
}
