package com.example.loginsystem.service;

import com.example.loginsystem.pojo.dto.OfferingCourseDTO;
import com.example.loginsystem.model.entity.Course;
import com.example.loginsystem.pojo.vo.CoursesVO;

import java.util.List;

public interface ICourseModify {

    void addCourse(OfferingCourseDTO offeringCourseDTO);
    void updateCourse(OfferingCourseDTO offeringCourseDTO);
    void deleteCourse(String courseCode);

    List<CoursesVO> getAllCourses();

    Course getCourseByCourseId(String courseId);




}
