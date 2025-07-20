package com.example.loginsystem.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CoursesVO implements java.io.Serializable{

    private  String courseCode;

    private String courseName;


    private Integer user_id;

    private Timestamp createAt;

    private Timestamp updateAt;





}
