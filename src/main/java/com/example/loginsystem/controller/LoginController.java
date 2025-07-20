package com.example.loginsystem.controller;


import com.example.loginsystem.pojo.dto.LoginDTO;
import com.example.loginsystem.service.ILoginService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final ILoginService iLoginService;

    @Autowired
    public LoginController( ILoginService iLoginService){
        this.iLoginService = iLoginService;
    }

    @RequestMapping( value = "/login", method = RequestMethod.POST )
    public ResponseEntity<String> Login(@Valid @RequestBody LoginDTO loginDTO) {
        // if auth then throw exception
        log.debug("開始處理登入業務,參數:{}", loginDTO);
        return ResponseEntity.ok(iLoginService.generateToken(loginDTO));
    }



}
