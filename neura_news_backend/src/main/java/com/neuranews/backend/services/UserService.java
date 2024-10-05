package com.neuranews.backend.services;

import com.neuranews.backend.models.User;
import com.neuranews.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private final UserRepository userRepo;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authManager,JWTService jwtService) {
        this.userRepo = userRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public void signup(User user) throws Exception {
        User oldUser = userRepo.findByEmail(user.getEmail());
        if(oldUser != null) {
            throw new Exception("User already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }


    public Map<String, String> login(User user) {
        Authentication auth =  authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (auth.isAuthenticated()) {
            System.out.println("Logged in as " + auth.getName());
            return Map.of("jwt", jwtService.generateToken(user), "refresh_token", jwtService.generateRefreshToken(user));
        }
        return null;
    }

    public User getUserByToken(String token) throws Exception {
        User user = userRepo.findByRefreshToken(token);

        if(user == null) {
            throw new UsernameNotFoundException("Invalid refresh token");
        }

        if(!jwtService.validateToken(token,user)){
            throw new Exception("Unauthorized Token");
        }

        return user;
    }
}


