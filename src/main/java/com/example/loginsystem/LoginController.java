package com.example.loginsystem;


import com.example.loginsystem.dto.LoginDTO;
import com.example.loginsystem.service.JwtService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(  AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder ){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping( value = "/login", method = RequestMethod.POST )
    public ResponseEntity<String> Login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) {
        System.out.println(loginDTO.getStudentNumber());
        System.out.println( loginDTO.getPassword() );
        System.out.println(passwordEncoder.encode(loginDTO.getPassword()));
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getStudentNumber(), loginDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 驗證成功後，從 Authentication 獲取用戶詳情
            String token = jwtService.generateToken(authentication, 2);
            // 使用認證後的用戶生成 JWT
            System.out.println(authentication.getPrincipal()); // UserDetails
            System.out.println(authentication.getAuthorities()); // 用戶權限
            return ResponseEntity.ok(token);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }


    }



}
