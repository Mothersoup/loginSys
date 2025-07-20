package com.example.loginsystem.pojo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {
    private static final String PATTERN_REGEXP_ACCOUNT = "^[A-Za-z0-9_]+$" ;
    private static final String PATTERN_MESSAGE_ACCOUNT = "Username should only contain letters, numbers and underscores" ;
    @Pattern(regexp = PATTERN_REGEXP_ACCOUNT, message = PATTERN_MESSAGE_ACCOUNT)
    @NotEmpty(message = "Username should not be empty")
    private String studentNumber;

    @NotEmpty(message = "Password should not be empty")
    private String password;


}
