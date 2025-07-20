package com.example.loginsystem.service.impl;

import com.example.loginsystem.pojo.dto.LoginDTO;
import com.example.loginsystem.ex.ServiceException;
import com.example.loginsystem.service.ILoginService;
import com.example.loginsystem.util.JwtUtil;
import com.example.loginsystem.web.ServerCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LoginServiceImp implements ILoginService
{
    private final   AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @Autowired
    public  LoginServiceImp(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }



    @Override
    public String generateToken(LoginDTO loginDTO) {
        log.debug("開始處理登入業務,參數:{}", loginDTO);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

// 檢查是否已認證，並且不是匿名使用者
        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) { // <-- 關鍵檢查點

            throw new ServiceException(ServerCode.ERR_USER_HAS_BEEN_LOGG, "User already logged in");
        }


        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(Integer.valueOf(loginDTO.getStudentNumber()), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

            // 驗證成功後，從 Authentication 獲取用戶詳情
        return jwtUtil.generateToken(authentication);

    }
}
