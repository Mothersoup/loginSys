package com.example.loginsystem.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/*
注意如果使用post 類的request 那麼json 格式裡面的parameter 要跟dto 或是 entity 名稱完全一樣
 */
@Entity
@Data
@Table( name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id",unique = true, nullable = false)
    private String studentNumber;

    @Column(name = "first_name", length = 100 )
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String firstName;

    @Column(name = "last_name", length = 100 )
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String lastName;


    @Column( name = "email", unique = true, length = 100 )
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
