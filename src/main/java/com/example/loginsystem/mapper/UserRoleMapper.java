package com.example.loginsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    /**
     * Find role names by student number.
     *
     * @param studentNumber the student number
     * @return a list of role names associated with the given student number
     */
    List<String> findRoleNamesByStudentNumber(@Param("user_id") Integer studentNumber);
}