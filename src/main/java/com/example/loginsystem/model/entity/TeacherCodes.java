package com.example.loginsystem.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "teacher_codes")
@Data
@NoArgsConstructor
public class TeacherCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "teacher_code", nullable = false, unique = true)
    private String teacherCode;

    @Column(name = "created_at")
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());


    public TeacherCodes( String teacherCode) {
        this.teacherCode = teacherCode;
    }
}
