package com.example.loginsystem.controller;

import com.example.loginsystem.pojo.dto.EnrollDTO;
import com.example.loginsystem.security.LoginPrinciple;
import com.example.loginsystem.service.IEnrollService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/enrollment")
@EnableMethodSecurity(prePostEnabled = true)
public class EnrollController {
    private final IEnrollService enrollService;

    public EnrollController(IEnrollService enrollService) {
        this.enrollService = enrollService;
    }


    // this method create for student look up for him or her self
    @RequestMapping(value = "/getAllRelatedCourses", method = RequestMethod.POST )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllRelatedCourses() {
        return ResponseEntity.ok(this.enrollService.getCoursesForStudent());
    }


    @RequestMapping(value = "/enrolled", method = RequestMethod.POST )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> EnrollStudent(@Valid @RequestBody EnrollDTO enrollDTO ) {
        this.enrollService.enrollStudentInCourse(enrollDTO.getCourse_code());
        return ResponseEntity.ok("enrolled success");
    }

    // this method create for teacher or admin check what courses the student enrolled in
    @RequestMapping(value =  "/getEnrolledStudents", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> getEnrolledStudents(EnrollDTO enrollDTO) {
        return ResponseEntity.ok(this.enrollService.getEnrolledStudents(enrollDTO.getCourse_code()));
    }
}
