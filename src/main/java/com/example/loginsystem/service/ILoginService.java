package com.example.loginsystem.service;

import com.example.loginsystem.pojo.dto.LoginDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * LoginServiceImpl業務層接口
 */
public  interface ILoginService {

    /**
     * 處理登入的方法
     * @param loginDTO 登入的數據
     * @return JWT數據
     */
    @Transactional(rollbackFor = {Exception.class})
    String generateToken(LoginDTO loginDTO);
}