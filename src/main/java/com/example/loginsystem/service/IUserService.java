package com.example.loginsystem.service;

import com.example.loginsystem.pojo.dto.SignUpDTO;
import com.example.loginsystem.model.entity.User;

public interface IUserService {
    User findStudentByStudentNum(Integer studentNum);
    User updateStudent(int id, User user);
    void RegisterNewUser(SignUpDTO signUpDTO);
    void saveStudent(User user);
    void deleteStudent(User user);
    boolean checkIfEmailExists(String email);
    User mapSignUpDTOToUser(SignUpDTO signUpDTO);

    void RegisterNewAdmin(SignUpDTO signUpDTO);

    void RegisterNewTeacher(SignUpDTO signUpDTO);


    String generateTeacherCode();
}
