package com.example.loginsystem;


import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class SignUpController {

    private final UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<String> registerStudent(@RequestBody User user) {
        if (userService.checkIfEmailExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }
        // setting create time
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp createTime = Timestamp.valueOf(localDateTime);
        user.setCreatedAt( createTime );
        ///
        userService.saveStudent(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student registered successfully");
    }


}
