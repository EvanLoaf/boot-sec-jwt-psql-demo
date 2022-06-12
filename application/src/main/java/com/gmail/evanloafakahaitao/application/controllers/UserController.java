package com.gmail.evanloafakahaitao.application.controllers;

import com.gmail.evanloafakahaitao.application.security.JwtProvider;
import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    public UserController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin_permission')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('admin_permission', 'user_permission')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
