package com.example.loginsystem.pojo.dto;


import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OfferingCourseDTO implements Serializable {

    @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$",
            message = "Course code must be in the format of 3 uppercase letters followed by 3 digits")
    private String course_code;
    private String course_name;

}
