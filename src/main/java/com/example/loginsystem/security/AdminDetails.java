package com.example.loginsystem.security;

<<<<<<< HEAD

=======
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
<<<<<<< HEAD
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Setter
@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdminDetails extends User {

    private Integer id;
    public AdminDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
=======
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
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180
