package com.example.loginsystem.controller;


import com.example.loginsystem.exception.EmailAlreadyExistException;
import com.example.loginsystem.model.dto.SignUpDTO;
import com.example.loginsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final UserService userService;


    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> signUp( @Valid @RequestBody SignUpDTO signUpDTO,  BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = formatBindingResultErrors(result);
            return ResponseEntity.badRequest().body(createErrorResponse("format", errorMessage));
        }

        try {
            userService.RegisterNewStudent(signUpDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createSuccessResponse("Student registered successfully"));
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
