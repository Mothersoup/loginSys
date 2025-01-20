package com.example.loginsystem.service;

import com.example.loginsystem.exception.EmailAlreadyExistException;
import com.example.loginsystem.repo.UserRepo;
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
            throw new EmailAlreadyExistException("Email already exists");
        }
    }
}