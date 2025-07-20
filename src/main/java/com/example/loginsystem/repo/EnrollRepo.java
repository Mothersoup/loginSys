package com.example.loginsystem.repo;

import com.example.loginsystem.model.entity.Enrollment;
import com.example.loginsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollRepo extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findAllByCourse_CourseCode(String courseCode);


    void deleteByCourse_CourseCodeAndStudent_StudentNumber(String courseCode, Integer studentId);


    boolean existsByCourse_CourseCodeAndStudent_StudentNumber(String courseCode, Integer studentId);


    List<Enrollment> findAllByStudent_StudentNumber(Integer studentId);


}
