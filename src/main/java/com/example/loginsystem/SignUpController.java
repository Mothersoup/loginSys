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
/*
 *
 *待新增功能email 驗證
 *
 *
 */
public class SignUpController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<String> registerStudent(@RequestBody SignUpDTO signUpDTO) {

        if (userService.checkIfEmailExists(signUpDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }
        // 設置創建時間
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp createTime = Timestamp.valueOf(localDateTime);

        // setting create time
        // 將 DTO 轉換為 User 實體
        User user = new User();
        user.setEmail(signUpDTO.getEmail());
        user.setLastName(signUpDTO.getLastName());
        user.setFirstName(signUpDTO.getFirstName());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setCreatedAt(createTime);
        ///
        userService.saveStudent(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student registered successfully");
    }


}
