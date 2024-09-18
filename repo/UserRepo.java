package com.example.loginsystem.repo;

import com.example.loginsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
@Repository
this is optional
 */
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByStudentNumber(String studentNumber);

    boolean existsByEmail(String email);
}
