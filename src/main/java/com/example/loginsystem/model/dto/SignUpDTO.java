package com.example.loginsystem.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpDTO {
    @Pattern(regexp = "^[A-Za-z]+$")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]+$")
    private String lastName;

    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String password;


}
