package com.neuranews.backend.models;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Setter
    private String name;

    @Setter
    @Email
    private String email;

    @Setter
    private String password;

    @Setter
    private String refreshToken;
}
