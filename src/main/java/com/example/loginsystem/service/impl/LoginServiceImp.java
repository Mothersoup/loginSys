package com.example.loginsystem.service.impl;

import com.example.loginsystem.dto.LoginDTO;
import com.example.loginsystem.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LoginServiceImp implements ILoginService
{
    private final   AuthenticationManager authenticationManager;
    @Autowired
    public  LoginServiceImp( AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }



    @Override
    public String login(LoginDTO loginDTO) {
        log.debug("開始處理登入業務,參數:{}", loginDTO);
//        Authentication authentication =
//                //將(帳號,密碼)傳入
//                new UsernamePasswordAuthenticationToken(loginDTO.getStudentNumber(), loginDTO.getPassword());

        return "";
    }
}
