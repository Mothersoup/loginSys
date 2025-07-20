package com.example.loginsystem.service.imp;

import com.alibaba.fastjson2.JSON;
import com.example.loginsystem.model.dto.LoginDTO;
import com.example.loginsystem.security.AdminDetails;
import com.example.loginsystem.service.ILoginService;
import com.example.loginsystem.util.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Service
@Log4j2
public class LoginServiceImp implements ILoginService {


    private AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    public LoginServiceImp(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService){
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }


    /**
     * this function takes authentication and verify logic complete in loadByUsername which username is user_id
     *
     */

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getUser_id(), loginDTO.getPassword() );
        Authentication loginResult = authenticationManager.authenticate(authentication);

        log.debug("登入成功:認證方法返回{}>>>>>{}", loginResult.getClass().getName(), loginResult);

        //從認證結果中 Principal,本質是User類型,是 UserDetailsService 中 loadUserByUsername() 的回傳結果
        log.debug("嘗試取得Principal:{}>>>{}", loginResult.getPrincipal().getClass().getName(), loginResult.getPrincipal());

        AdminDetails adminDetails = (AdminDetails) loginResult.getPrincipal();

        String userId = adminDetails.getUser_id();

        Collection<GrantedAuthority> authorities = adminDetails.getAuthorities();

        log.debug("登入成功時的權限:{}",authorities);

        String authoritiesString = JSON.toJSONString(authorities);

        log.debug("權限轉換為JSON:{}",authoritiesString);

        //需要封裝到JWT中的數據
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",userId);
        claims.put("authorities", authoritiesString);

        String jwt = JwtUtils.generateToken(claims);

        log.debug("生成的Jwt數據:{}", jwt);

        return jwt;
    }
}
