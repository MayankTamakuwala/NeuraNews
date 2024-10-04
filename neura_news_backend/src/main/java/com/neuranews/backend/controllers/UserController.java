package com.neuranews.backend.controllers;

import com.neuranews.backend.models.User;
import com.neuranews.backend.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        LOG.info("Getting all users.");
        return userService.getAllUsers();
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        userService.signup(user);
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> loginUsers(@RequestBody User user, HttpServletResponse response) {
            LOG.info("Login Hit.");
            Cookie cookie = new Cookie("jwt",userService.login(user));
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(10 * 60 * 60);

            response.addCookie(cookie);

            return Map.of("message", "Success");
    }
}
