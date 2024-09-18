package com.example.loginsystem.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Table( name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column( name = "student_id", length = 11)
    private int student_id;

    @Column( name = "course_id", length = 11)
    private int course_id;

    @Column(name = "enrolled_at", updatable = false)
    @CreationTimestamp
    private Timestamp enrolled_at;



}
