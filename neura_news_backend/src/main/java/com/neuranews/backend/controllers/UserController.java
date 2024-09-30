package com.neuranews.backend.controllers;

import com.neuranews.backend.models.User;
import com.neuranews.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users/")
    public List<User> getAllUsers() {
        LOG.info("Getting all users.");
        return userRepository.findAll();
    }

}
