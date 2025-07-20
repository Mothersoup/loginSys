package com.example.loginsystem.service;

import com.example.loginsystem.model.entity.Course;
import com.example.loginsystem.model.entity.User;

import java.util.List;

public interface IEnrollService {

    // for student
    void enrollStudentInCourse(String courseCode, Integer studentId);

    // for admin or teacher
    void enrollStudentInCourse(String courseCode );

    void unenrollStudentFromCourse(String courseCode, Integer studentId);

    boolean isStudentEnrolledInCourse(String courseCode, Integer studentId);


    List<User> getEnrolledStudents(String courseCode);


    List<Course> getCoursesForStudent(Integer studentId);

    List<Course> getCoursesForStudent();
}
