package com.example.loginsystem;


import com.example.loginsystem.model.dto.LoginDTO;
import com.example.loginsystem.util.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder passwordEncoder ){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping( value = "/login", method = RequestMethod.POST )
    public ResponseEntity<String> Login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUser_id(), loginDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Authentication stored in SecurityContextHolder: {}", authentication);

            UserDetails  user = (UserDetails) authentication.getPrincipal();
            // 驗證成功後，從 Authentication 獲取用戶詳情
            Map<String, Object> claims = Map.of(
                    "sub", user.getUsername(),
                    "roles", user.getAuthorities()
            );
            String token = jwtUtils.generateToken(claims);
            // 使用認證後的用戶生成 JWT
            return ResponseEntity.ok(token);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }


    }



}
