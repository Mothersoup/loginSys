package com.example.loginsystem.service;
<<<<<<< HEAD

import com.example.loginsystem.pojo.dto.LoginDTO;
=======
import com.example.loginsystem.model.dto.LoginDTO;
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180
import org.springframework.transaction.annotation.Transactional;

public interface ILoginService {


    /**
     * @param loginDTO 登入 DTO
     * @return JWT
     */
<<<<<<< HEAD
    @Transactional(rollbackFor = {Exception.class})
    String generateToken(LoginDTO loginDTO);
}
=======

    @Transactional( rollbackFor = {Exception.class})
    String login(LoginDTO loginDTO );
}
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180
