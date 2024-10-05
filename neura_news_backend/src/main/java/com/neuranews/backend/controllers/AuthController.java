package com.neuranews.backend.controllers;

import com.neuranews.backend.models.ResponseData;
import com.neuranews.backend.models.ResponseError;
import com.neuranews.backend.models.ResponseObject;
import com.neuranews.backend.models.User;
import com.neuranews.backend.services.JWTService;
import com.neuranews.backend.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private UserService userService;
    private JWTService jwtService;

    @Autowired
    public AuthController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/refresh")
    public ResponseObject refresh(@RequestBody RequestEntity<?> request, HttpServletResponse response) {
        List<String> authHeader = request.getHeaders().get("Authorization");
        User user;

        String token = authHeader.size() > 0 ? authHeader.get(0).substring(7) : null;

        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ResponseError("Refresh token not provided");
        }

        try {
            user = userService.getUserByToken(token);
        } catch (UsernameNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ResponseError(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ResponseError(e.getMessage());
        }

        Cookie cookie = new Cookie("jwt",jwtService.generateToken(user));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(10 * 60 * 60);
        response.addCookie(cookie);

        Date tokenExpirationDate = jwtService.extractExpiration(token);
        long remainingTime = tokenExpirationDate.getTime() - new Date().getTime();
        long twelveHoursInMillis = 12 * 60 * 60 * 1000;

        if (remainingTime < twelveHoursInMillis) {
            Cookie refreshCookie = new Cookie("refresh_token", jwtService.generateRefreshToken(user));
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(true);
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(refreshCookie);
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return new ResponseData("Refresh Successful");
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseObject loginUsers(@RequestBody User user, HttpServletResponse response) {
        LOG.info("Login Hit.");
        Map<String, String> tokens = userService.login(user);

        Cookie cookie = new Cookie("jwt",tokens.get("jwt"));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(10 * 60 * 60);
        response.addCookie(cookie);

        Cookie refreshCookie = new Cookie("refresh_token", tokens.get("refresh_token"));
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshCookie);

        return new ResponseData("Login Successful");
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
}
