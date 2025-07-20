package com.example.loginsystem.service.impl;

import com.example.loginsystem.ex.ServiceException;
import com.example.loginsystem.model.entity.Course;
import com.example.loginsystem.model.entity.Enrollment;
import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.repo.CoursesRepo;
import com.example.loginsystem.repo.EnrollRepo;
import com.example.loginsystem.repo.UserRepo;
import com.example.loginsystem.security.LoginPrinciple;
import com.example.loginsystem.service.IEnrollService;
import com.example.loginsystem.web.ServerCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;


@Service
public class EnrollServiceImp implements IEnrollService {
    private final EnrollRepo enrollRepo;
    private final UserRepo userRepo;
    private final CoursesRepo coursesRepo;
    public EnrollServiceImp(EnrollRepo enrollRepo, UserRepo userRepo, CoursesRepo coursesRepo) {
        this.enrollRepo = enrollRepo;
        this.userRepo = userRepo;
        this.coursesRepo = coursesRepo;
    }
    @Override
    public void enrollStudentInCourse(String courseCode, Integer studentId) {
        Enrollment enrollment = new Enrollment();
        User user = userRepo.findByStudentNumber(studentId)
                .orElseThrow(() -> new ServiceException(ServerCode.ERR_NOT_FOUND, studentId + " studentId not found "));
        Course course = coursesRepo.findByCourseCode(courseCode)
                .orElseThrow(() -> new ServiceException(ServerCode.ERR_NOT_FOUND, courseCode + " courseCode not found "));
        enrollment.setStudent(user);
        enrollment.setCourse(course);
        enrollment.setEnrolled_at(new java.sql.Timestamp(System.currentTimeMillis()));
        enrollRepo.save(enrollment);
    }

    @Override
    public void enrollStudentInCourse(String courseCode) {
        LoginPrinciple loginPrinciple = (LoginPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(userRepo.findByStudentNumber(Integer.parseInt(loginPrinciple.getAccount()))
                .orElseThrow(() -> new ServiceException(ServerCode.ERR_NOT_FOUND, "Student with student number " + loginPrinciple.getAccount() + " not found.")));
        enrollment.setCourse(coursesRepo.findByCourseCode(courseCode)
                .orElseThrow(() -> new ServiceException(ServerCode.ERR_NOT_FOUND, "Course with code " + courseCode + " not found.")));;

        enrollment.setEnrolled_at( new Timestamp(System.currentTimeMillis()));

        enrollRepo.save(enrollment);
    }

    @Override
    public void unenrollStudentFromCourse(String courseCode, Integer studentId) {
        enrollRepo.deleteByCourse_CourseCodeAndStudent_StudentNumber(courseCode, studentId);
    }

    @Override
    public boolean isStudentEnrolledInCourse(String courseCode, Integer studentId) {
        return enrollRepo.existsByCourse_CourseCodeAndStudent_StudentNumber(courseCode, studentId);
    }

    @Override
    public List<User> getEnrolledStudents(String courseCode) {

        return enrollRepo.findAllByCourse_CourseCode(courseCode)
                .stream()
                .map(Enrollment::getStudent)
                .toList();
    }

    @Override
    public List<Course> getCoursesForStudent(Integer studentId) {

        return enrollRepo.findAllByStudent_StudentNumber(studentId)
                .stream()
                .map(Enrollment::getCourse)
                .toList();
    }

    @Override
    public List<Course> getCoursesForStudent() {
        LoginPrinciple loginPrinciple = (LoginPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return enrollRepo.findAllByStudent_StudentNumber(Integer.parseInt(loginPrinciple.getAccount()))
                .stream()
                .map(Enrollment::getCourse)
                .toList();
    }


}
