package com.neuranews.backend.models;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    @Email
    private String email;

    @Setter
    @Getter
    private String password;
//    private final Date creationDate = new Date();

}
