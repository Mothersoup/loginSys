package com.example.loginsystem.pojo.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import reactor.util.annotation.Nullable;

import java.io.Serializable;

@Data
public class SignUpDTO implements Serializable {

    @Pattern(regexp = "^[0-9]{8}$",
            message = "Student number must be  digits"
    )
    private String studentNumber;



    @Pattern(regexp = "^[A-Za-z]+$",
            message = "fistname only can contain bigger and lower case A to Z"
    )
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]+$",
            message = "last name should not contain underline")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotNull(message = "email cannot be null")
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and contain letters and numbers"
    )
    @NotNull(message = "Password cannot be null")
    private String password;

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$",
            message = "teacher unique code"
    )
    @Nullable
    private String teacherUniqueCode;


}
