package com.example.loginsystem.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table( name = "attendancerecords")
public class AttendanceRecords implements Serializable {
    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY )
    private int id;
    @Column( name = "enrollment_id", length = 11, nullable = false)
    private int enroll_id;
    @Column( name = "attendance_date", nullable = false)
    private Date attendanceDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AttendanceStatus status;

    public enum AttendanceStatus {
        PRESENT, ABSENT, LATE
    }


    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}
