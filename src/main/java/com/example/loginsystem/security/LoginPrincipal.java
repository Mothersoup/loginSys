package com.example.loginsystem.security;

import lombok.Data;

import java.io.Serializable;


@Data
public class LoginPrincipal implements Serializable {

    private Integer id;
    private String account;


}
