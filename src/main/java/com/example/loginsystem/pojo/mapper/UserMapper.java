package com.example.loginsystem.pojo.mapper;


import com.example.loginsystem.model.entity.User;
<<<<<<< HEAD:src/main/java/com/example/loginsystem/pojo/mapper/UserMapper.java
import com.example.loginsystem.pojo.dto.SignUpDTO;
=======
import com.example.loginsystem.pojo.vo.LoginVo;
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180:src/main/java/com/example/loginsystem/mapper/UserMapper.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

<<<<<<< HEAD:src/main/java/com/example/loginsystem/pojo/mapper/UserMapper.java
@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper( PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(SignUpDTO signUpDTO) {
        User user = new User();
        user.setStudentNumber(Integer.valueOf(signUpDTO.getStudentNumber()));
        user.setEmail(signUpDTO.getEmail());
        user.setFirstName(signUpDTO.getFirstName());
        user.setLastName(signUpDTO.getLastName());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }

    public SignUpDTO toDto(User user) {
        SignUpDTO dto = new SignUpDTO();
        dto.setStudentNumber(String.valueOf(user.getStudentNumber()) )  ;
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }

=======
@Repository
public interface UserMapper {
    /**
     * 插入用户信息
     *
     * @return 插入的行数
     */
    LoginVo getByAccount(String account);
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180:src/main/java/com/example/loginsystem/mapper/UserMapper.java

}
