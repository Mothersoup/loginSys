package com.example.loginsystem.repo;

import com.example.loginsystem.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoursesRepo extends JpaRepository<Course, Integer> {
        void removeByCourseCode(String courseCode);
        boolean existsByCourseCode(String courseCode);

        Optional<Course> findByCourseCode(String courseCode);





}
