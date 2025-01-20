package com.example.loginsystem.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.io.Serializable;


@Data


public class LoginDTO implements Serializable {

    @NotEmpty(   message = "Username should not be empty" )
    @Pattern(regexp = PATTERN_REGEXP_ACCOUNT,  message = PATTERN_MESSAGE_ACCOUNT )
    private String studentNumber;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
