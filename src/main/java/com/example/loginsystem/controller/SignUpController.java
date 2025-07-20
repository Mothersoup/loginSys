package com.example.loginsystem.controller;


import com.example.loginsystem.pojo.dto.GenerateTeacherCodeDTO;
import com.example.loginsystem.exception.EmailAlreadyExistException;
import com.example.loginsystem.pojo.dto.SignUpDTO;
import com.example.loginsystem.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
/*
 *
 *待新增功能email 驗證
 *
 *
 */
public class SignUpController {

    private final IUserService iUserService;

    @Autowired
    public SignUpController(IUserService iUserService) {
        this.iUserService = iUserService;
    }


    @RequestMapping(value = "/signUp/user", method = RequestMethod.POST)
    public ResponseEntity<?> signUp( @Valid @RequestBody SignUpDTO signUpDTO,  BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = formatBindingResultErrors(result);
            return ResponseEntity.badRequest().body(createErrorResponse("format", errorMessage));
        }

        try {
            iUserService.RegisterNewUser(signUpDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createSuccessResponse("Student registered successfully"));
        } catch (EmailAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(createErrorResponse("Conflict", e.getMessage()));
        }


    }

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/signUp/admin", method = RequestMethod.POST)
    public ResponseEntity<?> createAdmin(@Valid @RequestBody SignUpDTO signUpDTO, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = formatBindingResultErrors(result);
            return ResponseEntity.badRequest().body(createErrorResponse("format", errorMessage));
        }

        try {
            iUserService.RegisterNewAdmin(signUpDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createSuccessResponse("Admin registered successfully"));
        } catch (EmailAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(createErrorResponse("Conflict", e.getMessage()));
        }
    }


    /**
     * this method is used to generate  a teacher unique code for teacher registration
     * email teacher email
     */

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN') or hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/invite/teacher", method = RequestMethod.POST)
    public ResponseEntity<?> inviteTeacher(@Valid @RequestBody GenerateTeacherCodeDTO generateTeacherCodeDTO, BindingResult result) {

        if (result.hasErrors()) {
            String errorMessage = formatBindingResultErrors(result);
            return ResponseEntity.badRequest().body(createErrorResponse("format", errorMessage));
        }

        try {
            String teacherCode = iUserService.generateTeacherCode();
//            iMailService.sendEmail( generateTeacherCodeDTO.getEmail(), "teacher register code",  teacherCode );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createSuccessResponse("Teacher registration code sent successfully to " + generateTeacherCodeDTO.getEmail()));
        } catch (EmailAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(createErrorResponse("Conflict", e.getMessage()));
        }

    }

    private String formatBindingResultErrors(BindingResult result) {
        StringBuilder errorMessage = new StringBuilder();
        result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
        return errorMessage.toString();
    }

    private Map<String, Object> createSuccessResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        return response;
    }


    private Map<String, Object> createErrorResponse(String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("error", error);
        response.put("message", message);
        return response;
    }
}
