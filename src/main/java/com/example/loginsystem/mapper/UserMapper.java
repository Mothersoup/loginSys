package com.example.loginsystem.mapper;


import com.example.loginsystem.dto.SignUpDTO;
import com.example.loginsystem.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper( PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(SignUpDTO signUpDTO) {
        User user = new User();
        user.setEmail(signUpDTO.getEmail());
        user.setFirstName(signUpDTO.getFirstName());
        user.setLastName(signUpDTO.getLastName());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }

    public SignUpDTO toDto(User user) {
        SignUpDTO dto = new SignUpDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }


}
