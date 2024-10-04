package com.neuranews.backend.services;

import com.neuranews.backend.models.User;
import com.neuranews.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

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


    public String login(User user) {
        Authentication auth =  authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (auth.isAuthenticated()) {
            System.out.println("Logged in as " + auth.getName());
            return jwtService.generateToken(user);
        }
        return null;
    }
}


