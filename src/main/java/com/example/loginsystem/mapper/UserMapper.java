package com.example.loginsystem.mapper;


import com.example.loginsystem.dto.SignUpDTO;
import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.pojo.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public interface UserMapper {
    /**
     * 插入用户信息
     *
     * @return 插入的行数
     */
    LoginVo getByAccount(String account);

}
