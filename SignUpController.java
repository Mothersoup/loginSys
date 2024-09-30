package com.example.loginsystem;


import com.example.loginsystem.model.dto.SignUpDTO;
import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class SignUpController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public SignUpController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<String> registerStudent(@RequestBody SignUpDTO signUpDTO ) {
        if (userService.checkIfEmailExists( signUpDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }
        User user = new User();
        // setting create time
        user.setEmail( signUpDTO.getEmail() );
        user.setPassword( passwordEncoder.encode( signUpDTO.getPassword()) );
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp createTime = Timestamp.valueOf(localDateTime);
        user.setCreatedAt( createTime );
        ///
        userService.saveStudent(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student registered successfully");
    }


}
