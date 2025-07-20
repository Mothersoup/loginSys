package com.example.loginsystem.service;

import com.example.loginsystem.ex.ServiceException;
import com.example.loginsystem.exception.EmailAlreadyExistException;
import com.example.loginsystem.repo.UserRepo;
import com.example.loginsystem.web.ServerCode;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {
    private final UserRepo userRepo;

    @Autowired
    public UserValidationService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void validateEmailUniqueness(String email) {
        if (userRepo.existsByEmail(email)) {
            throw new ServiceException(ServerCode.ERR_INSERT, "Email already exists");
        }
    }
    public void validateStudentNumberUniqueness(Integer studentNumber) {
        if (userRepo.existsByStudentNumber(studentNumber)) {
            throw new ServiceException(ServerCode.ERR_INSERT, "Student number already exists");
        }
    }
}