package com.neuranews.backend.controllers;

import com.neuranews.backend.models.ResponseData;
import com.neuranews.backend.models.ResponseError;
import com.neuranews.backend.models.ResponseObject;
import com.neuranews.backend.models.User;
import com.neuranews.backend.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("/api")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseData server() {
        return new ResponseData("NeuraNews Spring Boot Server!");
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseData getAllUsers() {
        LOG.info("Getting all users.");
        return new ResponseData(userService.getAllUsers());
    }

}
