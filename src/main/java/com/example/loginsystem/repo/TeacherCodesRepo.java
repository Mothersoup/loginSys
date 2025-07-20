package com.example.loginsystem.repo;

import com.example.loginsystem.model.entity.TeacherCodes;
import com.example.loginsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TeacherCodesRepo extends JpaRepository<TeacherCodes, Integer> {



    Optional<TeacherCodes> findByTeacherCode(String teacherCode);

    /**
     * Check if a TeacherCodes entity exists by its code.
     *
     * @param code the code to check
     * @return true if a TeacherCodes entity with the given code exists, false otherwise
     */
    boolean existsByTeacherCode(String code);


    @Transactional
    @Modifying
    @Query("UPDATE teacher_codes t SET t.user = :user WHERE t.teacherCode = :teacherCode")
    void updateUserByTeacherCode(@Param("teacherCode") String teacherCode,
                                 @Param("user") User user);
}
