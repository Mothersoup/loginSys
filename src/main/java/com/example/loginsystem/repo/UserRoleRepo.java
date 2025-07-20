package com.example.loginsystem.repo;

import com.example.loginsystem.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer>{


    Optional<UserRole> findByUser_StudentNumber(Integer userId);


    // search userRole by role name
    List<UserRole> findByRole_RoleName(String roleName);
}
