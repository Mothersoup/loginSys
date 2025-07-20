package com.example.loginsystem.pojo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminSignUpDTO implements Serializable {

    @Pattern(regexp = "^[0-9]{8,}$",
            message = "Admin number must be at least 8 digits"
    )
    private String adminNumber;
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and contain letters and numbers")
    private String password;

    private String firstName;
    private String lastName;
    @Pattern(regexp = "^[A-Za-z]+$",
            message = "First name should only contain letters")
    private String email;
}
