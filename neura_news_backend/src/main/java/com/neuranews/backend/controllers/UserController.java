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

@RestController
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseData getAllUsers() {
        LOG.info("Getting all users.");
        return new ResponseData(userService.getAllUsers());
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseObject signup(@RequestBody User user, HttpServletResponse response) throws Exception {
        try {
            userService.signup(user);
            return new ResponseData("Sign Up Successfull");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ResponseError(e.getMessage());
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseObject loginUsers(@RequestBody User user, HttpServletResponse response) {
            LOG.info("Login Hit.");
            Cookie cookie = new Cookie("jwt",userService.login(user));
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(10 * 60 * 60);

            response.addCookie(cookie);
            return new ResponseData("Login Successfull");
    }
}
