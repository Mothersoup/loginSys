package com.example.loginsystem.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table( name = "userroles")
@Data
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int studentId;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;  // 關聯到 Role 實體
}