package com.example.loginsystem.pojo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;


@Data
public class GetEnrolledStudentDTO implements Serializable {

    @Pattern(regexp = "^[A-Z]{3}-[0-9]{3}$", message = "Invalid course code format. Expected format: XXX-000")
    String courseCode;

}
