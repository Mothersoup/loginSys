package com.example.loginsystem.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    private int id;

    @Column(name = "role_name", unique = true, nullable = false, length = 50)
    private String roleName;

}
