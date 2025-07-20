package com.example.loginsystem.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role implements Serializable {
    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    private int id;

    @Column(name = "role_name", unique = true, nullable = false, length = 50)
    private String roleName;

    public Role( String roleName) {
        this.roleName = roleName;
    }
}
