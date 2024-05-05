package com.mohdeveloper.blogplatform.model;

import com.mohdeveloper.blogplatform.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class SignupUser {
    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Password is Required")
    private String password;

    private String confirmPassword;
    public User toUser(){
        return new User(email, password);
    }

}
