package com.example.loginsystem.controller;


import com.example.loginsystem.pojo.dto.DeleteCourseDTO;
import com.example.loginsystem.pojo.dto.OfferingCourseDTO;
import com.example.loginsystem.service.ICourseModify;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/course")
@EnableMethodSecurity(prePostEnabled = true)
public class CourseController {

    private final ICourseModify courseModifyService;

    public CourseController(ICourseModify courseModifyService) {
        this.courseModifyService = courseModifyService;
    }


    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @RequestMapping(value = "/offering", method = RequestMethod.POST)
    public ResponseEntity<?> offeringCourse( @Valid @RequestBody OfferingCourseDTO offeringCourseDTO) {
        this.courseModifyService.addCourse(offeringCourseDTO);
        return ResponseEntity.ok("Course offering functionality is under development.");
    }

    @PreAuthorize("hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
    @RequestMapping(value =  "/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateCourse(@Valid @RequestBody OfferingCourseDTO offeringCourseDTO) {
        this.courseModifyService.updateCourse(offeringCourseDTO);
        return ResponseEntity.ok("Course updated successfully.");
    }

    @PreAuthorize("hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
    @RequestMapping(value =  "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteCourse(@Valid @RequestBody DeleteCourseDTO deleteCourseDTO) {
        this.courseModifyService.deleteCourse(deleteCourseDTO.getCourse_code());
        return ResponseEntity.ok("Course deleted successfully.");
    }


    @RequestMapping(value =  "/getAllCourses", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(this.courseModifyService.getAllCourses());
    }









}
