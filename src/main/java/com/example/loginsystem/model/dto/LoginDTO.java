package com.example.loginsystem.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor



public class LoginDTO {

    @NotEmpty( message = "email should not be empty" )
    private String user_id;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
