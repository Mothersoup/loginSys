package com.example.loginsystem.service.impl;

import com.example.loginsystem.ex.ServiceException;
import com.example.loginsystem.model.entity.UserRole;
import com.example.loginsystem.repo.UserRoleRepo;
import com.example.loginsystem.service.IUserRoleRepo;
import com.example.loginsystem.web.ServerCode;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleRepoImp implements IUserRoleRepo {


    private final UserRoleRepo userRoleRepo;

    public UserRoleRepoImp(UserRoleRepo userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    public UserRole getUserRoleByUserId(Integer userId) {
    return userRoleRepo.findByUser_StudentNumber(userId).orElseThrow(() -> new ServiceException(ServerCode.ERR_NOT_FOUND, userId.toString()));
    }
}
