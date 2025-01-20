package com.example.loginsystem.model.dto;

import jakarta.validation.constraints.NotEmpty;
<<<<<<< HEAD
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.io.Serializable;


@Data


public class LoginDTO implements Serializable {

    @NotEmpty(   message = "Username should not be empty" )
    @Pattern(regexp = PATTERN_REGEXP_ACCOUNT,  message = PATTERN_MESSAGE_ACCOUNT )
=======
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
>>>>>>> 991b84608a6da86a65138d4f6dce3e65f00ea8b3
    private String studentNumber;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
