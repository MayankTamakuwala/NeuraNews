package com.neuranews.backend.repository;

import com.neuranews.backend.models.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(@Email String email);
    User findByRefreshToken(String refreshToken);
}