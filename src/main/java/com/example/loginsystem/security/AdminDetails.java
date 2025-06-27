package com.example.loginsystem.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class AdminDetails extends User  {

    private String user_id;

    public AdminDetails(String account, String password, boolean enable, Collection<? extends GrantedAuthority> authorities) {
        super(account, password, enable, true, true, true, authorities);

    }
}