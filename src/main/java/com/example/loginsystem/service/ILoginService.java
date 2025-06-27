package com.example.loginsystem.service;
import com.example.loginsystem.model.dto.LoginDTO;
import org.springframework.transaction.annotation.Transactional;

public interface ILoginService {


    /**
     * @param loginDTO 登入 DTO
     * @return JWT
     */

    @Transactional( rollbackFor = {Exception.class})
    String login(LoginDTO loginDTO );
}
