package com.example.loginsystem.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor



public class LoginDTO {

    @NotEmpty( message = "Username should not be empty" )
    private String studentNumber;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
